package ch.zhaw.bollmannmarques.Devices

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import ch.zhaw.bollmannmarques.Calc.CalcPointForPendel
import ch.zhaw.bollmannmarques.Helper.Controller
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * This is the physical device. this device  reacts if some forces impact it. In this case, it is a pendel. It reacts on: gravity and Impact devices
 * @author  raphi
 */
class Pendulum extends PendulumInterface {
  //
  //  @BeanProperty
  //  var pi: MotorInterface=_

  @BeanProperty
  var x = Array(1.0)

  @BeanProperty
  var y = Array(1.0)

  @BeanProperty
  var length: Double = _

  @BeanProperty
  var acceleration: Double = 100

  @BeanProperty
  var velocity: Double = _

  @BeanProperty
  var dumping: Double = 1.15

  @BeanProperty
  var fi: Double = _

  @BeanProperty
  var m: Double = 1

  x(0) = 600

  y(0) = 100

  //  override def setX(px: Array[Double]){
  //    x = px
  //  }
  //
  //  override def setY(y: Array[Double]){}

  def this(pi:MotorInterface){
    this()
    this.calculateLength(pi)
  }

  def setLength(length: Double, pi: MotorInterface) {
    this.length = length
    val a = Math.sin((fi) - Math.PI / 2) * length
    val b = Math.cos((fi) - Math.PI / 2) * length
    x(0) = pi.getX(0) + b
    y(0) = pi.getY(0) - a
    acceleration = 100
  }

  override def calculateLength(pi: MotorInterface) {
    length = Math.sqrt(Math.pow(x(0) - pi.getX(0), 2) + Math.pow(y(0) - pi.getY(0), 2))
  }

  override def getDraw(gc: Graphics2D, pi: MotorInterface): Graphics2D = {
    gc.setStroke(new BasicStroke(5))
    gc.setPaint(Color.green)
    gc.drawLine(Math.round(pi.getX(0)).toInt, Math.round(pi.getY(0)).toInt, Math.round(getX()(0)).toInt,
      Math.round(getY()(0)).toInt)
    gc.setPaint(Color.red)
    gc.fillOval(Math.round(pi.getX(0) - 10).toInt, Math.round(pi.getY(0) - 10).toInt, 20, 20)
    gc.fillOval(Math.round(getX()(0) - 10).toInt, Math.round(getY()(0) - 10).toInt, 20, 20)
    gc
  }

  override def setNFi(nn: Double, c: Controller) {
    var n = nn;
    if (n > Math.PI) {
      n = n - Math.PI * 2
    }
    if (n < -Math.PI) {
      n = n + Math.PI * 2
    }
    fi = n
    CalcPointForPendel.fiToPendel(c)
  }
}
