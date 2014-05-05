package ch.zhaw.bollmannmarques.GUI

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.util.Random
import javax.swing.JComponent
import ch.zhaw.bollmannmarques.Helper.Controller
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._
import java.lang.String
/**
 * Visualisation of the Dravings on the Graphical Interface (Canvas)
 *
 * @author raphi
 */
class GraphField(pc2: Controller, @BeanProperty var fps: Double, var h: Double)
    extends JComponent with Runnable {

  private var pc: Controller = pc2

  /**
   * Start the animation (the Class DisplayCanvas extends a thread)
   */
  def run() {
    try {
      while (true) {
        for (i <- 0 until ((1 / fps * 1000) / h).toInt) {
          pc.nextstep(h)
          Thread.sleep(((h)).toLong)
        }
        repaint()
      }
    } catch {
      case ie: InterruptedException => 
    }
  }

  /**
   * This paints the content once. It is also used for "repaint", standard
   * JComponent Method.
   */
  override
  def paint(g: Graphics) {
    var gc = pc.getMotor.getDraw(g)
    gc = pc.getPendulum.getDraw(gc, pc.getMotor)
    gc.drawString("Angle:", 200, 500)
    gc.drawString(String.valueOf(Math.abs(Math.floor(1000 * Math.toDegrees(pc.getPendulum.getFi)) / 
      1000) + 
      " °"), 400, 500)
    if (pc.getPendulum.getFi < 0) {
      gc.drawString("-", 395, 500)
    }
    gc.drawString("Shuttle Speed:", 200, 515)
    gc.drawString(String.valueOf(Math.abs(Math.floor(1000 * Math.toDegrees(pc.getPendulum.getVelocity) / 1000)) + 
      " °/s"), 400, 515)
    if (pc.getPendulum.getVelocity < 0) {
      gc.drawString("-", 395, 515)
    }
    gc.drawString("Motor Position:", 200, 530)
    gc.drawString(String.valueOf(Math.abs(Math.floor(1000 * pc.getMotor().getX(0)) / 1000)),
      400, 530)
    gc.drawString("Motor Speed:", 200, 545)
    if (pc.getMotor.getActualv < 0) {
      gc.drawString("-", 395, 545)
    }
    gc.drawString(String.valueOf(Math.abs(Math.floor(1000 * pc.getMotor.getActualv) / 1000) + 
      " px/s"), 400, 545)
    gc.setPaint(Color.magenta)
    gc.drawString("Leveling off at:", 200, 560)
    gc.drawString(String.valueOf(Math.abs(Math.floor(1000 * pc.getMotor.getMitte) / 1000) + 
      " px"), 400, 560)
  }
}
