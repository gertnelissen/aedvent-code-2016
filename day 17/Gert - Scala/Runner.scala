package main.scala.Day17

import java.security.MessageDigest

object Runner extends Runner {}

class Runner {
  type Direction = (Char, Int, Int)
  type Position = (Int, Int)
  type State = (Position, String)

  val gridSize = 4
  val vaultLocation = (3, 3)

  def run(input: String): String = {
    Iterator.iterate(List(((0, 0), input)))(states => states.flatMap(iterate))
      .find(states => states.exists(state => vaultReached(state))).get
      .find(vaultReached).get._2.drop(input.length)
  }

  def vaultReached(state: State): Boolean = state._1 == vaultLocation

  def iterate(state: State): IndexedSeq[State] = {
    getOptions(md5(state._2))
        .map(option => (option._1, (state._1._1 + option._2, state._1._2 + option._3)))
        .filter(option => validPosition(option._2))
        .map(option => (option._2, state._2 + option._1))
  }

  def validPosition(position: (Int, Int)): Boolean = {
    position._1 >= 0 && position._1 < gridSize &&
      position._2 >= 0 && position._2 < gridSize
  }


  def getOptions(s: String): IndexedSeq[Direction] = {
    s.take(4).zipWithIndex.map(c => (c._2, isOpen(c._1))).filter(_._2).map(o => getDirection(o._1))
  }

  def isOpen(c: Char): Boolean = {
    c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f'
  }

  def getDirection(option: Int): Direction = {
    option match {
      case 0 => ('U', -1, 0)
      case 1 => ('D', 1, 0)
      case 2 => ('L', 0, -1)
      case 3 => ('R', 0, 1)
    }
  }

  def md5(s: String): String = {
    MessageDigest.getInstance("MD5").digest(s.getBytes)
      .map("%02X".format(_)).mkString("").toLowerCase.replace("-", "")
  }
}