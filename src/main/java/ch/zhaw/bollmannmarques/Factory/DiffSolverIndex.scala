package ch.zhaw.bollmannmarques.Factory

//remove if not needed
import scala.collection.JavaConversions._

object DiffSolverIndex extends Enumeration {

  val RUNGEKUTTA = new DiffSolverIndex()

  val REGEL38 = new DiffSolverIndex()

  class DiffSolverIndex extends Val

  implicit def convertValue(v: Value): DiffSolverIndex = v.asInstanceOf[DiffSolverIndex]
}
