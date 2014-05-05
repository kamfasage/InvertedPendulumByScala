package ch.zhaw.bollmannmarques.InverseAlgorithms

import java.util.ArrayList
import ch.zhaw.bollmannmarques.Calc.DiffEq
import ch.zhaw.bollmannmarques.Calc.InversePendulumPhysic
import ch.zhaw.bollmannmarques.Devices.Motor
import ch.zhaw.bollmannmarques.Devices.MotorInterface
import ch.zhaw.bollmannmarques.Devices.Pendulum
import ch.zhaw.bollmannmarques.Devices.PendulumInterface
import ch.zhaw.bollmannmarques.Helper.Controller
import ch.zhaw.bollmannmarques.Helper.ExactMPController
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._
// import following package
import scala.util.control._

/**
 * The class recovery algorithm is to make inversely to the pendulum.
 *
 * @author Roger
 *
 */
class RecoveryAlgorithm(var c: Controller) {

  private var rot: Int = 0

  private var ref: ArrayList[Fivcombo] = new ArrayList[Fivcombo]()

  private var above: Boolean = _

  private var oldv: Double = _

  @BeanProperty
  var i: Int = 0

  /**
   * Calculations in the document
   * @param a angle of Pendenl
   * @param v Speed ​​from the pendulum
   * @param averagev Average speed of the motor
   */
  def aufschwingen(a: Double, v: Double, averagev: Double) {
    var delta = eval()
    var newMaxV = c.getMotor.getMaxv
    var procv = 1
    if (c.getMotor().getX(0) <
      c.getMotor.getMin + (c.getMotor.getMaxv * 100) / c.getMotor.getMaxacc) {
      procv = 0
    } else if (c.getMotor().getX(0) >
      c.getMotor.getMax - (c.getMotor.getMaxv * 100) / c.getMotor.getMaxacc) {
      procv = 0
    }
    newMaxV = newMaxV * procv
    if (Math.abs(c.getPendulum.getFi) < Math.PI / 2) {
      above = false
    }
    if (Math.abs(c.getPendulum.getFi) > Math.PI / 2) {
      above = true
    }
    var as = a
    var b: Int = 0
    var vs = v
    vs = Math.abs(vs)
    if (a * v > 0) {
      as = Math.abs(a)
    }
    if (a * v < 0) {
      as = -Math.abs(a)
    }
    b = 0
    // create a Breaks object as follows
    val loop = new Breaks;

    // Keep the loop inside breakable as follows
    loop.breakable {
      for (j <- 0 until ref.size if ref.get(j).getFi > as) {
        b = j
        loop.break
      }
    }
    var x: Double = 0.0
    x = if (c.getPendulum.getFi < Math.toRadians(160) && c.getPendulum.getFi > Math.toRadians(-160)) 0 else if (c.getPendulum.getFi * (c.getMotor().getX(0) - c.getMotor.getMitte) <
      0) c.getMotor.getMaxacc * Math.abs(averagev) /
      (c.getMotor.getMaxv * Math.pow(Math.abs(c.getPendulum.getFi), 4)) else c.getMotor.getMaxacc * Math.abs(averagev) /
      (c.getMotor.getMaxv * Math.pow(Math.abs(c.getPendulum.getFi), 4))
    if (b != 0) {
      rot = if (vs < ref.get(b - 1).getV + x) 1 else if (vs > ref.get(b).getV + x) -1 else 0
      c.getMotor.setAcceleration(c.getMotor.getMaxacc)
      while (ref.get(b - 1).getV < delta) {
        c.getMotor.setAcceleration(c.getMotor.getAcceleration / 2)
        delta = eval()
      }
    }
    val pv = c.getPendulum.getVelocity
    val mx = c.getMotor().getX(0)
    if ((rot == -1 && pv < 0 && mx <= 500 && above) || (rot == -1 && mx <= 500 && pv >= 0 && !above) ||
      (rot == 1 && mx <= 500 && pv >= 0 && above) ||
      (rot == 1 && mx <= 500 && pv < 0 && !above)) {
      c.getMotor.setVelocity(c.getMotor.getMaxv)
      if (oldv * pv < 0) {
        c.getMotor.setVelocity(-c.getMotor.getMaxv)
      }
    }
    if ((rot == -1 && pv < 0 && mx <= 500 && !above) || (rot == -1 && mx <= 500 && pv >= 0 && above) ||
      (rot == 1 && mx <= 500 && pv >= 0 && !above) ||
      (rot == 1 && mx <= 500 && pv < 0 && above)) {
      c.getMotor.setVelocity(-newMaxV)
      if (oldv * pv < 0) {
        c.getMotor.setVelocity(c.getMotor.getMaxv)
      }
    }
    if ((rot == -1 && pv < 0 && mx > 500 && above) || (rot == -1 && mx > 500 && pv >= 0 && !above) ||
      (rot == 1 && mx > 500 && pv >= 0 && above) ||
      (rot == 1 && mx > 500 && pv < 0 && !above)) {
      c.getMotor.setVelocity(newMaxV)
      if (oldv * pv < 0) {
        c.getMotor.setVelocity(-c.getMotor.getMaxv)
      }
    }
    if ((rot == -1 && pv < 0 && mx > 500 && !above) || (rot == -1 && mx > 500 && pv >= 0 && above) ||
      (rot == 1 && mx > 500 && pv >= 0 && !above) ||
      (rot == 1 && mx > 500 && pv < 0 && above)) {
      c.getMotor.setVelocity(-c.getMotor.getMaxv)
      if (oldv * pv < 0) {
        c.getMotor.setVelocity(c.getMotor.getMaxv)
      }
    }
    oldv = pv
    if ((c.getPendulum.getFi < -Math.toRadians(150) || c.getPendulum.getFi > Math.toRadians(150))) {
      if (c.getPendulum.getFi * c.getPendulum.getVelocity < 0) {
        if (c.getPendulum.getFi < 0) {
          c.getMotor.setVelocity(-newMaxV)
        } else {
          c.getMotor.setVelocity(newMaxV)
        }
      }
      i += 1
    } else {
      i = 0
    }
  }

  def setRef(ref2: ArrayList[Fivcombo]) {
    this.ref = ref2
  }

  /**
   * Evaluates whether the acceleration of the motor is too big.
   * @return
   */
  def eval(): Double = {
    val m = new Motor()
    m.setAcceleration(c.getMotor.getAcceleration)
    val p = new Pendulum(m)
    p.setLength(c.getPendulum.getLength, m)
    p.setM(c.getPendulum.getM)
    p.setDumping(c.getPendulum.getDumping)
    p.setVelocity(0)
    val co = new ExactMPController(p, m)
    p.setNFi(-3.141, co)
    val g = Array.ofDim[DiffEq](1)
    g(0) = new InversePendulumPhysic(co)
    co.setG(g)
    co.setSimulate(true)
    m.setVelocity(c.getMotor.getMaxv)
    val a = co.getPendulum.getVelocity
    co.nextstep(10)
    co.nextstep(10)
    co.nextstep(10)
    val b = co.getPendulum.getVelocity
    b - a
  }
}
