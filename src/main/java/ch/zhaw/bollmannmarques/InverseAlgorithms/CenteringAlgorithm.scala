package ch.zhaw.bollmannmarques.InverseAlgorithms

import ch.zhaw.bollmannmarques.Helper.Controller
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Find the delta value to the ideal angle for centering.
 * @author raphi
 *
 */
class CenteringAlgorithm(var c: Controller) {

  @BeanProperty
  var i: Int = 0

  private var mitte: Double = _

  /**
   * More in the document
   * @param averagevc average speed
   * @return idealwinkel
   */
  def evaluate(averagevc: Double): Double = {
    val m = c.getMotor.getMitte
    val vplavgv = Math.abs(c.getPendulum.getLength * Math.abs(averagevc) / c.getMotor.getMaxv) * 
      5
    val vx = (Math.abs(m - c.getMotor().getX(0)))
    if (mitte != c.getMotor.getMitte) {
      i = 0
    }
    mitte = c.getMotor.getMitte
    var count = (vplavgv + vx) / 100
    if (count > 5) {
      count = 5
    } else if (count < -5) {
      count = -5
    }
    val md = c.getMotor().getX(0) - m
    if (c.getMotor().getX(0) < m) {
      count = -count
    }
    if (averagevc * md < 0) {
      count = count / 100
    }
    count = Math.toRadians(count)
    if (c.getMotor().getX(0) < m + c.getPendulum.getLength && 
      c.getMotor().getX(0) > m - c.getPendulum.getLength) {
      this.i += 1
    } else {
      this.i = 0
    }
    -count
  }
}
