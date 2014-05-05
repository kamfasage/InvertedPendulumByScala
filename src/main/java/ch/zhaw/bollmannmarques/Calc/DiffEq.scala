package ch.zhaw.bollmannmarques.Calc

/**
 * Interface for all Differencial Equations. Used by rungeKutta
 * @author   raphi
 */
abstract trait DiffEq {
  def getVariables: Array[Double]

  def setVariables(n: Array[Double])

  def evaluate(x: Array[Double], k: Array[Double]): Array[Double]

  def getCalc: Array[Boolean]

  def reset
}