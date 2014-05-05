package ch.zhaw.bollmannmarques.Devices

import java.awt.Graphics
import java.awt.Graphics2D

/**
 * This class is the interface for the device motor.
 * @author Roger
 *
 */
abstract trait MotorInterface {
  def getX: Array[Double]

  def getY: Array[Double]

  def setX(integer: Array[Double])

  def setY(double1: Array[Double])

  def setAcceleration(acceleration: Double)

  def getAcceleration: Double

  def setVelocity(velocity: Double)

  def getVelocity: Double

  def getDraw(g: Graphics): Graphics2D

  def getMax: Double

  def getActualv: Double

  def setActualv(v: Double)

  def setMax(max: Double)

  def getMin: Double

  def setMin(min: Double)

  def getMitte: Double

  def setMitte(mitte: Double)

  def getMaxv: Double

  def setMaxv(maxv: Double)

  def getMaxacc: Double

  def setMaxacc(acc: Double)
}