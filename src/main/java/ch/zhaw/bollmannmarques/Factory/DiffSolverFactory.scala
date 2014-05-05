package ch.zhaw.bollmannmarques.Factory

import ch.zhaw.bollmannmarques.Calc.DiffEq
import ch.zhaw.bollmannmarques.Calc.InversePendulumPhysic
import ch.zhaw.bollmannmarques.DiffSolver.DiffSolver
import ch.zhaw.bollmannmarques.DiffSolver.Regel38
import ch.zhaw.bollmannmarques.DiffSolver.RungeKutta
import ch.zhaw.bollmannmarques.Helper.ExactMPController
//remove if not needed
import scala.collection.JavaConversions._
import DiffSolverIndex._

object DiffSolverFactory {

  def getDiffSolverInstance(g: Array[DiffEq], selector: DiffSolverIndex): DiffSolver = selector match {
    case RUNGEKUTTA => new RungeKutta(g(0))
    //http://en.wikipedia.org/wiki/Simpson's_rule
    case REGEL38 => new Regel38(g(0))
    case _ => new RungeKutta(g(0))
  }
}
