package ch.zhaw.bollmannmarques.DiffSolver

import ch.zhaw.bollmannmarques.Calc.DiffEq
//remove if not needed
import scala.collection.JavaConversions._

/**
 * This is the 3/8 regel approximation class. this class implementns the 3/8
 * regel approximation of a equation. The equation needs to be in the right
 * format.
 *
 * @author Roger
 *
 */
class Regel38(var ode: DiffEq) extends DiffSolver {

  var inp: Array[Double] = _

  var k1: Array[Double] = _

  var k2: Array[Double] = _

  var k3: Array[Double] = _

  var k4: Array[Double] = _

  /**
   * This method calculates the new values after x seconds.
   *
   * @param stepSize
   *            what time needs to be evaluated (in seconds)
   */
  def nextStep(stepSize: Double): Array[Double] = {
    val vars = ode.getVariables
    val N = vars.length
    if ((inp == null) || (inp.length != N)) {
      inp = Array.ofDim[Double](N)
      k1 = Array.ofDim[Double](N)
      k2 = Array.ofDim[Double](N)
      k3 = Array.ofDim[Double](N)
      k4 = Array.ofDim[Double](N)
    }
    var i: Int = 0
    k1 = ode.evaluate(vars, k1)
    i = 0
    while (i < N) {
      inp(i) = vars(i) + k1(i) * stepSize / 3
      i += 1
    }
    k2 = ode.evaluate(inp, k2)
    i = 0
    while (i < N) {
      inp(i) = vars(i) + k1(i) * stepSize * -1 / 3 + k2(i) * stepSize
      i += 1
    }
    k3 = ode.evaluate(inp, k3)
    i = 0
    while (i < N) {
      inp(i) = vars(i) + k1(i) * stepSize - k2(i) * stepSize + k3(i) * stepSize
      i += 1
    }
    k4 = ode.evaluate(inp, k4)
    val calc = ode.getCalc
    i = 0
    while (i < vars.length) {
      if (calc(i)) vars(i) = vars(i) +
        (k1(i) + 3 * k2(i) + 3 * k3(i) + k4(i)) * stepSize / 8
      i += 1
    }
    vars
  }
}
