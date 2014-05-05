package ch.zhaw.bollmannmarques.InverseAlgorithms

/**
 * Comparable container for the ArrayList.
 * @author raphi
 *
 */
class Fivcombo extends Comparable[Fivcombo] {
  def this(fi2: Double, velocity: Double) {
    this()
    fi = fi2
    v = velocity
  }

  def getFi: Double = {
    return fi
  }

  def setFi(fi: Double) {
    this.fi = fi
  }

  def getV: Double = {
    return v
  }

  def setV(v: Double) {
    this.v = v
  }

  def compareTo(o: Fivcombo): Int = {
    if (this.getFi > o.getFi) {
      return 1
    }
    else if (this.getFi > o.getFi) {
      return -1
    }
    else {
      return 0
    }
  }

  private var fi: Double = .0
  private var v: Double = .0
}