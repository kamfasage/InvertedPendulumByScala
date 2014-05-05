package ch.zhaw.bollmannmarques.Factory

//remove if not needed
import scala.collection.JavaConversions._

object MotorEnum extends Enumeration {

  val MOTOR = new MotorEnum()

  class MotorEnum extends Val

  implicit def convertValue(v: Value): MotorEnum = v.asInstanceOf[MotorEnum]
}
