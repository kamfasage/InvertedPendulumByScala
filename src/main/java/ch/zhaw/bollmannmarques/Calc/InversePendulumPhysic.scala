package ch.zhaw.bollmannmarques.Calc

import ch.zhaw.bollmannmarques.Helper.Controller

/**
 * This is the differencial equiation for Runge Kutta. It is in RungeKutta format.
 *
 * @author Roger
 */
class InversePendulumPhysic extends DiffEq {
  /**
   * Initiates the Object with the variables in the controller
   * @param c controller with all relevant informations
   */
  def this(c: Controller) {
    this()
    this.c = c
    reset
  }

  def reset {
    vars = new Array[Double](5)
    vars(0) = (c.getPendulum.getFi)
    vars(1) = c.getPendulum.getVelocity
    vars(2) = c.getMotor.getX(0)
    vars(3) = c.getMotor.getVelocity
    vars(4) = 0
    fi = (c.getPendulum.getFi)
  }

  def getVariables: Array[Double] = {
    return vars
  }

  def setVariables(n: Array[Double]) {
    this.vars = n
  }

  /**
   * This it the step which solves the different equations for Runge Kutta.
   * @param x all variables for x
   * @param k all variables for k
   *
   */
  def evaluate(x: Array[Double], k: Array[Double]): Array[Double] = {
    val g: Double = 9.81
    val b: Double = c.getPendulum.getDumping
    val length: Double = c.getPendulum.getLength
    val b0: Double = c.getMotor.getAcceleration
    k(0) = x(1)
    k(1) = -k(3) / length * Math.cos(x(0)) - g / length * Math.sin(x(0)) - b / (c.getPendulum.getM * Math.pow(length, 2)) * x(1)
    k(2) = x(3)
    if (c.getMotor.getVelocity == x(3)) {
      k(3) = 0
    }
    else if (c.getMotor.getVelocity > x(3)) {
      if ((c.getMotor.getVelocity - b0 / 10) < x(3)) {
        k(3) = (c.getMotor.getVelocity - x(3)) * 10
      }
      else {
        k(3) = b0 / c.getPendulum.getM
      }
    }
    else if (c.getMotor.getVelocity < x(3)) {
      if ((c.getMotor.getVelocity - b0 / 10) > x(3)) {
        k(3) = -(c.getMotor.getVelocity - x(3)) * 10
      }
      else {
        k(3) = -b0 / c.getPendulum.getM
      }
    }
    k(4) = 1
    return k
  }

  /**
   * get Length of the variables.
   */
  override def getCalc(): Array[Boolean] = {
    val t = Array.ofDim[Boolean](vars.length)
    for (i <- 0 until t.length) {
      t(i) = true
    }
    t
  }

  private var fi: Double = .0
  private var c: Controller = null
  private var vars: Array[Double] = null
}