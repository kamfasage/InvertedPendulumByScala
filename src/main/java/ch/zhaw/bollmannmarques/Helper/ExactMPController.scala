package ch.zhaw.bollmannmarques.Helper

import ch.zhaw.bollmannmarques.Calc.DiffEq
import ch.zhaw.bollmannmarques.Devices.PendulumInterface
import ch.zhaw.bollmannmarques.Devices.MotorInterface
import ch.zhaw.bollmannmarques.DiffSolver.DiffSolver
import ch.zhaw.bollmannmarques.DiffSolver.Regel38
import ch.zhaw.bollmannmarques.Factory.DiffSolverFactory
import ch.zhaw.bollmannmarques.Factory.DiffSolverIndex._
import ch.zhaw.bollmannmarques.InverseAlgorithms.InverseMaker
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Logic of our app,
 * Instantiates all relevant objects (using the factory). Calculated with an approximation di simulation and provide the algorithm.
 * @author raphi
 *
 */
class ExactMPController()
    extends Controller {
@BeanProperty 
var pd: PendulumInterface = _
@BeanProperty 
var pi: MotorInterface = _
  private var rk: DiffSolver = _

  @BeanProperty
  var go: Boolean = false

  //@BeanProperty
  var g: Array[DiffEq] = _

  private var im: InverseMaker = new InverseMaker(this)

  @BooleanBeanProperty
  var simulate: Boolean = _
  
  private var dfi: DiffSolverIndex = _
  
  def this(pd: PendulumInterface, pi: MotorInterface) {  
    this()
    this.pd = pd
    this.pi = pi
    dfi = REGEL38
  }

  def this(pd: PendulumInterface, pi: MotorInterface, g: Array[DiffEq]) {  
    this()
    this.pd = pd
    this.pi = pi
    this.g = g
//    dfi = DiffSolverIndex.REGEL38
    dfi = REGEL38
    setG(g)
  }

  def getDiffSolver(): DiffSolver = rk

  def setDiffSolver(rk: DiffSolver) {
    this.rk = rk
  }

  def getPendulum(): PendulumInterface = pd

  def setPendulum(pd: PendulumInterface) {
    this.pd = pd
  }

  def getMotor(): MotorInterface = pi

  def setMotor(pi: MotorInterface) {
    this.pi = pi
  }

  /**
   * With a new equation, the new approximation is instantiated each.
   */
  def setG(g: Array[DiffEq]) {
    this.g = g
    rk = DiffSolverFactory.getDiffSolverInstance(g, dfi)
  }
  
  def getG() : Array[DiffEq] = g

  def getDiffSolverEnum(): DiffSolverIndex = dfi

  def setDiffSolverEnum(dfi: DiffSolverIndex) {
    this.dfi = dfi
  }

  /**
   * To set new values ​​(the "Reset" button in the GUI)
   */
  def reset() {
    g(0).reset
    rk = DiffSolverFactory.getDiffSolverInstance(g, dfi)
  }

  /**
   * Calculates each time window for simulation, hands over to algorithm
   */
  override def nextstep(h: Double) {
    if (!simulate) {
      if (go) {
        im.evalute(pd.getVelocity, pd.getFi)
      } else {
        pi.setVelocity(0)
      }
    }
    val vars = rk.nextStep(h / 100)
    pd.setVelocity(vars(1))
    pi.getX(0) = vars(2)
    pi.setActualv(vars(3))
    pd.setNFi((vars(0)), this)
    pd.calculateLength(pi)
    vars(0) = (pd.getFi)
  }

  /**
   * Does the algorithm. This is only one interface to the algorithm (Only the controller know the algorithms).
   */
  def precalc() {
    im.precalc
  }
}
