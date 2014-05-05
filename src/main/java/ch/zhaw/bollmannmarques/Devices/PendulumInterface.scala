package ch.zhaw.bollmannmarques.Devices

import java.awt.Graphics2D
import ch.zhaw.bollmannmarques.Helper.Controller

/**
 * This class is the interface for the device pendulum
 * @author Roger
 *
 */
abstract trait PendulumInterface {
  def getM: Double

  def setM(m: Double)

  def getX: Array[Double]

  def getY: Array[Double]

  def setX(double1: Array[Double])

  def setY(double1: Array[Double])

  def getLength: Double

  def calculateLength(pi: MotorInterface)

  def setAcceleration(acceleration: Double)

  def getAcceleration: Double

  def setVelocity(velocity: Double)

  def getVelocity: Double

  def setDumping(dumping: Double)

  def getDumping: Double

  def setNFi(n: Double, c: Controller)

  def getFi: Double

  def getDraw(gc: Graphics2D, pi: MotorInterface): Graphics2D

  def setLength(length: Double, pi: MotorInterface)
}