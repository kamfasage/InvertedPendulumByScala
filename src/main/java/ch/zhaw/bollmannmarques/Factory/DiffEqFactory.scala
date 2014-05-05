package ch.zhaw.bollmannmarques.Factory

import ch.zhaw.bollmannmarques.Calc.DiffEq
import ch.zhaw.bollmannmarques.Calc.InversePendulumPhysic
import ch.zhaw.bollmannmarques.Devices.PendulumInterface
import ch.zhaw.bollmannmarques.Devices.MotorInterface
import ch.zhaw.bollmannmarques.Helper.Controller
import ch.zhaw.bollmannmarques.Helper.ExactMPController
//remove if not needed
import scala.collection.JavaConversions._
import DiffEqIndex._

object DiffEqFactory {

  def generate(selector: DiffEqIndex, 
      pd: PendulumInterface, 
      pi: MotorInterface, 
      h: Double): Controller = {
    var c: Controller = null
    var g: Array[DiffEq] = null
    selector match {
      case INVERSEPENDULUMPHYSIC => 
        c = new ExactMPController(pd, pi)
        g = Array.ofDim[DiffEq](1)
        g(0) = new InversePendulumPhysic(c)
        c.setG(g)
        c

      case _ => throw new Exception("Physical EQ not found")
    }
  }
}
