package ch.zhaw.bollmannmarques.Factory

import ch.zhaw.bollmannmarques.Devices.PendulumInterface
import ch.zhaw.bollmannmarques.Devices.MotorInterface
import ch.zhaw.bollmannmarques.Helper.Controller
//remove if not needed
import scala.collection.JavaConversions._
import DiffEqIndex._

object Factory {

  def getMaths(selector: Array[String], h: Double): Controller = {
    var motor: MotorInterface = null
    var pendulum: PendulumInterface = null
    val s = Array.ofDim[String](7)
    for (i <- 0 until selector.length) {
      s(i) = selector(i)
    }
//    motor = if (s(0) == null || MotorEnum.withName(s(0)) == null) MotorFactory.generate(MotorEnum.MOTOR) else MotorFactory.generate(MotorEnum.valueOf(s(0)))
    motor = MotorFactory.generate(MotorEnum.MOTOR)
//    pendulum = if (s(1) == null || PendulumEnum.valueOf(s(1)) == null) PendulumFactory.generate(PendulumEnum.PENDULUM, 
//      motor) else PendulumFactory.generate(PendulumEnum.valueOf(s(1)), motor)
    pendulum = PendulumFactory.generate(PendulumEnum.PENDULUM, 
      motor)
    if (s(3) != null) {
      motor.getX(0) = (new java.lang.Double(s(3)))
    }
    if (s(4) != null) {
      motor.getY(0) = (new java.lang.Double(s(4)))
    }
    if (s(5) != null) {
      pendulum.getX(0) = (new java.lang.Double(s(5)))
    }
    if (s(6) != null) {
      pendulum.getY(0) = (new java.lang.Double(s(6)))
    }
    var c: Controller = null
//    c = if (s(2) == null || DiffEqIndex.valueOf(s(2)) == null) DiffEqFactory.generate(DiffEqIndex.INVERSEPENDULUMPHYSIC, 
//      pendulum, motor, h) else DiffEqFactory.generate(DiffEqIndex.valueOf(s(2)), pendulum, motor, h)
    c = DiffEqFactory.generate(DiffEqIndex.INVERSEPENDULUMPHYSIC, 
      pendulum, motor, h)
    c
  }
}
