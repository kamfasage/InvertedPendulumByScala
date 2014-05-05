package ch.zhaw.bollmannmarques.Helper

import ch.zhaw.bollmannmarques.Calc.DiffEq
import ch.zhaw.bollmannmarques.Devices.PendulumInterface
import ch.zhaw.bollmannmarques.Devices.MotorInterface
import ch.zhaw.bollmannmarques.DiffSolver.DiffSolver
import ch.zhaw.bollmannmarques.Factory.DiffSolverIndex._
//remove if not needed
import scala.collection.JavaConversions._
/**
 * @author   raphi
 */
trait Controller {

  def getPendulum(): PendulumInterface

  def getMotor(): MotorInterface

  def getG(): Array[DiffEq]

  def setPendulum(pd: PendulumInterface): Unit

  def setMotor(pi: MotorInterface): Unit

  def setG(g: Array[DiffEq]): Unit

  def nextstep(h: Double): Unit

  def reset(): Unit

  def getGo(): Boolean

  def setGo(go: Boolean): Unit

  def precalc(): Unit

  def setSimulate(b: Boolean): Unit

  def isSimulate(): Boolean

  def setDiffSolver(ds: DiffSolver): Unit

  def getDiffSolver(): DiffSolver

  def getDiffSolverEnum(): DiffSolverIndex

  def setDiffSolverEnum(dfi: DiffSolverIndex): Unit
}
