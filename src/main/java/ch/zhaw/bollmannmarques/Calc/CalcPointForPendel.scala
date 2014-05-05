package ch.zhaw.bollmannmarques.Calc

import ch.zhaw.bollmannmarques.Helper.Controller

/**
 * This class calculates all points of a pendel. This is needed afterwards to the pendel.
 * @author raphi
 *
 *
 */
object CalcPointForPendel {
  /**
   *
   * @param c controller which have all informations.
   * @return
   */
  def fiToPendel(c: Controller): Controller = {
    val fi = c.getPendulum.getFi
    val a = Math.sin(fi - Math.PI / 2) * c.getPendulum.getLength
    val b = Math.cos(fi - Math.PI / 2) * c.getPendulum.getLength
    c.getPendulum().getX(0) = (c.getMotor().getX(0) + b)
    c.getPendulum().getY(0) = (c.getMotor().getY(0) - a)
    c
  }
}