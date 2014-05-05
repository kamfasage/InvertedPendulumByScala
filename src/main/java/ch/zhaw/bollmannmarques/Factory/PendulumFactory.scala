package ch.zhaw.bollmannmarques.Factory

import ch.zhaw.bollmannmarques.Devices.Pendulum
import ch.zhaw.bollmannmarques.Devices.PendulumInterface
import ch.zhaw.bollmannmarques.Devices.MotorInterface
//remove if not needed
import scala.collection.JavaConversions._
import PendulumEnum._
object PendulumFactory {

  def generate(selector: PendulumEnum, pi: MotorInterface): PendulumInterface = selector match {
    case PENDULUM => new Pendulum(pi)
    case _ => throw new Exception("Physical Device not found")
  }
}
