package ch.zhaw.bollmannmarques.DiffSolver

/**
 * This class is the interface for calculation classes
 * @author Roger
 *
 */
abstract trait DiffSolver {
  def nextStep(h: Double): Array[Double]
}