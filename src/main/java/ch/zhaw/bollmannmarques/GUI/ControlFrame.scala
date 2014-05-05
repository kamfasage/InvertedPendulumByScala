package ch.zhaw.bollmannmarques.GUI

import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import ch.zhaw.bollmannmarques.Factory.DiffSolverIndex
import ch.zhaw.bollmannmarques.Factory.Factory
import ch.zhaw.bollmannmarques.Helper.Controller
//remove if not needed
import scala.collection.JavaConversions._
import ch.zhaw.bollmannmarques.Factory.DiffEqIndex._
import ch.zhaw.bollmannmarques.Factory.DiffSolverIndex._
/**
 * This part of the GUI handles the interaction to the user. It provides the
 * part below in the interface (Controls).
 *
 * @author raphi
 */
class ControlFrame(selector: Array[String], 
    guiMain: GUIMain, 
    ph: Double, 
    i2fps: Double) extends JPanel {

  private var gm: GUIMain = guiMain

  private var h: Double = 1000 / (ph * i2fps)

  private var fps: Double = ph

  private var pta: JTextField = _

  private var ptv: JTextField = _

  private var ptal: JTextField = _

  private var ptl: JTextField = _

  private var ptw: JTextField = _

  private var mtx: JTextField = _

  private var mtv: JTextField = _

  private var approxmethod: JComboBox = _

  private var t: Thread = _

  private var gf: GraphField = _

  private var f: JFrame = _

  private var pc: Controller = Factory.getMaths(selector, h)

  private var mta: JTextField = _

  val manager = KeyboardFocusManager.getCurrentKeyboardFocusManager

  manager.addKeyEventDispatcher(new MyDispatcher())

  init()

  /**
   * Build the Graph and everything
   * @throws Exception
   */
  def init() {
    buildControls()
    gf = new GraphField(pc, fps, h)
    gf.setSize(1400, 600)
    this.setSize(1400, 200)
    t = new Thread(gf)
    t.start()
    f = gm.getF
    val gl = new GridBagLayout()
    val c = new GridBagConstraints()
    f.setLayout(gl)
    c.fill = GridBagConstraints.HORIZONTAL
    c.ipady = 600
    c.gridx = 0
    c.gridy = 0
    f.add(gf, c)
    c.fill = GridBagConstraints.HORIZONTAL
    c.ipady = 200
    c.gridx = 0
    c.gridy = 1
    f.add(this, c)
  }

  /**
   * Biuild buttons
   */
  private def buildControls() {
    this.setLayout(new FlowLayout())
    val ptit = new JLabel("Pendulum Properties:   ")
    val pa = new JLabel("Resistance:")
    pta = new JTextField("1.15")
    val pv = new JLabel("Speed:")
    ptv = new JTextField("0")
    val pal = new JLabel("Angle:")
    ptal = new JTextField("0")
    val pl = new JLabel("Length:")
    ptl = new JTextField("200")
    val pw = new JLabel("Weight:")
    ptw = new JTextField("1")
    val mtit = new JLabel("Motor Features:   ")
    val mx = new JLabel("X:")
    mtx = new JTextField("500")
    val mv = new JLabel("Max-Speed:")
    mtv = new JTextField("100")
    val ma = new JLabel("Max-Acceleration:")
    mta = new JTextField("100")
    approxmethod = new JComboBox(DiffSolverIndex.values.toArray[Object])
    approxmethod.setSelectedItem(pc.getDiffSolverEnum)
    val set = new JButton("Values S​et")
    val los = new JButton("Raise Pendulum")
    this.add(ptit)
    this.add(pa)
    this.add(pta)
    this.add(pv)
    this.add(ptv)
    this.add(pal)
    this.add(ptal)
    this.add(pl)
    this.add(ptl)
    this.add(pw)
    this.add(ptw)
    this.add(mtit)
    this.add(mx)
    this.add(mtx)
    this.add(mv)
    this.add(mtv)
    this.add(ma)
    this.add(mta)
    this.add(approxmethod)
    this.add(set)
    this.add(los)
    set.addActionListener(new ActionListener() {

      override def actionPerformed(arg0: ActionEvent) {
        t.interrupt()
        try {
        } catch {
          case e: Exception => e.printStackTrace()
        }
        val mtxd = Array.ofDim[Double](1)
        mtxd(0) = new java.lang.Double(mtx.getText)
        pc.getMotor.setX(mtxd)
        pc.getMotor.setMaxv(new java.lang.Double(mtv.getText))
        pc.getMotor.setMaxacc(new java.lang.Double(mta.getText))
        pc.getPendulum.setDumping(new java.lang.Double(pta.getText))
        pc.getPendulum.setVelocity(new java.lang.Double(ptv.getText))
        pc.getPendulum.setNFi((new java.lang.Double(ptal.getText)), pc)
        pc.getPendulum.setM(new java.lang.Double(ptw.getText))
        pc.getPendulum.setLength(new java.lang.Double(ptl.getText), pc.getMotor)
        pc.setDiffSolverEnum(approxmethod.getSelectedItem.asInstanceOf[DiffSolverIndex])
        pc.reset()
        pc.precalc()
        t = new Thread(gf)
        t.start()
      }
    })
    los.addActionListener(new ActionListener() {

      override def actionPerformed(e: ActionEvent) {
        if (!pc.getGo) {
          pc.precalc()
          pc.setGo(true)
        } else {
          pc.setGo(false)
        }
      }
    })
  }

  private class MyDispatcher extends KeyEventDispatcher {

    override def dispatchKeyEvent(e: KeyEvent): Boolean = {
      val a = e.getKeyCode
      if (e.getID == KeyEvent.KEY_PRESSED) {
        if (e.getKeyCode == KeyEvent.VK_LEFT) {
          pc.getMotor.setMitte(pc.getMotor.getMitte - 1)
        } else if (e.getKeyCode == KeyEvent.VK_RIGHT) {
          pc.getMotor.setMitte(pc.getMotor.getMitte + 1)
        }
      } else if (e.getID == KeyEvent.KEY_RELEASED) {
        if (e.getKeyCode == KeyEvent.VK_LEFT) {
          pc.getMotor.setMitte(pc.getMotor.getMitte - 1)
        } else if (e.getKeyCode == KeyEvent.VK_RIGHT) {
          pc.getMotor.setMitte(pc.getMotor.getMitte + 1)
        }
      } else if (e.getID == KeyEvent.KEY_TYPED) {
        if (e.getKeyCode == KeyEvent.VK_LEFT) {
          pc.getMotor.setMitte(pc.getMotor.getMitte - 1)
        } else if (e.getKeyCode == KeyEvent.VK_RIGHT) {
          pc.getMotor.setMitte(pc.getMotor.getMitte + 1)
        }
      }
      false
    }
  }
}
