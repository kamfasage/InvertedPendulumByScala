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
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Algorithm for stabilization at the zero angle
 * @author raphi
 *
 */
class StabilizationAlgorithm(var c: Controller, var aa: RecoveryAlgorithm, var za: CenteringAlgorithm)
    {

  private var lastv: Double = _

  private var directx: Boolean = _

  private var ref: ArrayList[Fivcombo] = _

  /**
   * More detailed description of the document
   * @param averagevc average speed
   * @param center ideal angle
   */
  def evaluate(averagevc: Double, center: Double) {
    c.getMotor.setAcceleration(c.getMotor.getMaxacc)
    if (za.getI > 1000) {
      c.getMotor.setAcceleration(c.getMotor.getMaxacc / (za.getI / 1000))
    }
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
    var finorm: Double = 0.0
    finorm = if (c.getPendulum.getFi < 0) -Math.PI - c.getPendulum.getFi else Math.PI - c.getPendulum.getFi
    lastv = c.getPendulum.getVelocity
    directx = if (c.getPendulum.getVelocity * c.getPendulum.getFi > 0) true else false
    if (!directx) {
      if (finorm < center) {
        c.getMotor.setVelocity(-newMaxV)
      } else {
        c.getMotor.setVelocity(newMaxV)
      }
    }
    if (directx) {
      c.getMotor.setVelocity(c.getMotor.getActualv * procv)
    }
    if ((c.getPendulum.getFi > 0 && c.getPendulum.getFi < Math.toRadians(150)) || 
      (c.getPendulum.getFi > Math.toRadians(-150) && c.getPendulum.getFi < 0)) {
      aa.setI(0)
    }
  }

  def setRef(ref: ArrayList[Fivcombo]) {
    this.ref = ref
  }

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
