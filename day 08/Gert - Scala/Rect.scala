package main.scala.Day08
import scala.util.matching.Regex

object Rect extends InstructionCompanion {
  override val pattern: Regex = "rect (\\d+)x(\\d+)".r
}

class Rect(x: Int, y: Int) extends Instruction {
  override def execute(grid: Array[Array[Boolean]]): Unit = {
    (0 until y).foreach(row => {
      (0 until x).foreach(column => grid(row)(column) = true)
    })
  }
}
