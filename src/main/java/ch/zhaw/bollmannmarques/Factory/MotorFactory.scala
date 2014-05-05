package ch.zhaw.bollmannmarques.Factory

import ch.zhaw.bollmannmarques.Devices.Motor
import ch.zhaw.bollmannmarques.Devices.MotorInterface
//remove if not needed
import scala.collection.JavaConversions._
import MotorEnum._
object MotorFactory {

  def generate(selector: MotorEnum): MotorInterface = selector match {
    case MOTOR => new Motor()
    case _ => throw new Exception("Physical Device not found")
  }
}
