package ch.zhaw.bollmannmarques.Factory

//remove if not needed
import scala.collection.JavaConversions._

object PendulumEnum extends Enumeration {

  val PENDULUM = new PendulumEnum()

  class PendulumEnum extends Val

  implicit def convertValue(v: Value): PendulumEnum = v.asInstanceOf[PendulumEnum]
}
