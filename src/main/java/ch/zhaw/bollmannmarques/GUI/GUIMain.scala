package ch.zhaw.bollmannmarques.GUI

import javax.swing.JFrame
import javax.swing.UIManager
import scala.beans.BeanProperty

//remove if not needed


object GUIMain {

  def main(args: Array[String]) {
    try {
      new GUIMain(args)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}

/**
 * MainClass which created a basic UI with a DrawField in Swing.
 * @author  raphi
 */
class GUIMain(selector: Array[String]) {

  @BeanProperty
  var f: JFrame = new JFrame("Physics Simulator")

  val fps = 50

  val i2fps = 2

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  f.setSize(1400, 800)

  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  new ControlFrame(selector, this, fps, i2fps)

  f.setVisible(true)
}
