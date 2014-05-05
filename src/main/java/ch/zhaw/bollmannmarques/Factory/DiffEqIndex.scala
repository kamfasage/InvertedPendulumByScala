package ch.zhaw.bollmannmarques.Factory

//remove if not needed
import scala.collection.JavaConversions._

object DiffEqIndex extends Enumeration {

  val INVERSEPENDULUMPHYSIC = new DiffEqIndex()

  class DiffEqIndex extends Val

  implicit def convertValue(v: Value): DiffEqIndex = v.asInstanceOf[DiffEqIndex]
}
