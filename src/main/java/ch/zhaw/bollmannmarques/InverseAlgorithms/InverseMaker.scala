package ch.zhaw.bollmannmarques.InverseAlgorithms

import java.util.ArrayList
import java.util.Collections
import ch.zhaw.bollmannmarques.Calc.DiffEq
import ch.zhaw.bollmannmarques.Calc.InversePendulumPhysic
import ch.zhaw.bollmannmarques.Devices.Motor
import ch.zhaw.bollmannmarques.Devices.MotorInterface
import ch.zhaw.bollmannmarques.Devices.Pendulum
import ch.zhaw.bollmannmarques.Devices.PendulumInterface
import ch.zhaw.bollmannmarques.Helper.Controller
import ch.zhaw.bollmannmarques.Helper.ExactMPController

/**
 * Passes to the different algorithms. If in principle the transition manager.
 * @author raphi
 *
 */
class InverseMaker {
  def this(c: Controller) {
    this()
    this.c = c
    aa = new RecoveryAlgorithm(c)
    za = new CenteringAlgorithm(c)
    sa = new StabilizationAlgorithm(c, aa, za)
  }

  /**
   * Evaluert algorithm which must be taken
   * @param v speed pendulum
   * @param a angle-bob
   */
  def evalute(v: Double, a: Double) {
    averagev(i % 20) = c.getMotor.getActualv
    var averagevc: Double = 0
    var j: Int = 0
    while (j < averagev.length) {
      {
        averagevc = averagevc + averagev(j)
      }
      ({
        j += 1; j - 1
      })
    }
    averagevc = averagevc / 20
    averagevl(i % 200) = c.getMotor.getActualv
    var averagevcl: Double = 0
    var k: Int = 0
    while (k < averagevl.length) {
      {
        averagevcl = averagevcl + averagevl(k)
      }
      ({
        k += 1; k - 1
      })
    }
    averagevcl = averagevcl / 200
    i += 1
    if (aa.getI < 100) {
      za.setI(0)
      aa.aufschwingen(a, v, averagevc)
    }
    else {
      sa.evaluate(averagevc, za.evaluate(averagevc))
      if (za.getI > 500) {
        sa.evaluate(averagevc, za.evaluate(averagevcl))
      }
    }
  }

  /**
   * to fast, speed of pendulum is minus
   */
  def precalc {
    val m: MotorInterface = new Motor
    val p: PendulumInterface = new Pendulum(m)
    p.setLength(c.getPendulum.getLength, m)
    p.setM(c.getPendulum.getM)
    p.setDumping(0)
    p.setVelocity(0)
    val co: Controller = new ExactMPController(p, m)
    p.setNFi(-3.141, co)
    val g: Array[DiffEq] = new Array[DiffEq](1)
    g(0) = new InversePendulumPhysic(co)
    co.setG(g)
    while (co.getPendulum.getVelocity >= 0) {
      co.nextstep(0.1)
      val f: Fivcombo = new Fivcombo((co.getPendulum.getFi), co.getPendulum.getVelocity)
      ref.add(f)
    }
    Collections.sort(ref)
    aa.setRef(ref)
    sa.setRef(ref)
  }

  private var ref: ArrayList[Fivcombo] = new ArrayList[Fivcombo]
  private var averagev: Array[Double] = new Array[Double](20)
  private var averagevl: Array[Double] = new Array[Double](200)
  private var i: Int = 0
  private var c: Controller = null
  private var aa: RecoveryAlgorithm = null
  private var sa: StabilizationAlgorithm = null
  private var za: CenteringAlgorithm = null
}