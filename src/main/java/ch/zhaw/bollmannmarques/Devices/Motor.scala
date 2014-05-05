package ch.zhaw.bollmannmarques.Devices

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Container for a physical device which gives force to the system. It is a container for all attributes
 * @author  raphi
 */
class Motor extends MotorInterface {

  @BeanProperty
  var x: Array[Double] = new Array[Double](1)

  @BeanProperty
  var y: Array[Double] = new Array[Double](1)

  // @BeanProperty
  var maxacc: Double = 150

  @BeanProperty
  var actualv: Double = _

  @BeanProperty
  var max: Double = 1100

  @BeanProperty
  var min: Double = 100

  @BeanProperty
  var acceleration: Double = 50

  @BeanProperty
  var velocity: Double = _

  @BeanProperty
  var maxv: Double = 100

  @BeanProperty
  var mitte: Double = 700

  x(0) = 700

  y(0) = 250

  def getMaxacc(): Double=maxacc

  def setMaxacc(maxacc: Double) {
    this.maxacc = maxacc
    this.acceleration = maxacc
  }

  /**
   * Drives the motor icon in to a graphfield (canvas)
   */
  override def getDraw(g: Graphics): Graphics2D = {
    val gc = g.asInstanceOf[Graphics2D]
    gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    gc.setStroke(new BasicStroke(15))
    gc.drawLine(100, 265, 1100, 265)
    gc.setPaint(Color.blue)
    gc.fillRect(Math.round(getX()(0) - 20).toInt, Math.round(getY()(0) - 20).toInt, 40, 40)
    gc.setPaint(Color.magenta)
    gc.drawRect(Math.round(getMitte - 5).toInt, Math.round(getY()(0) + 30).toInt, 10, 10)
    gc
  }
}
