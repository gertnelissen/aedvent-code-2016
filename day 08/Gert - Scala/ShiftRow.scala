package main.scala.Day08
import scala.util.matching.Regex

object ShiftRow extends InstructionCompanion {
  override val pattern: Regex = "rotate row y=(\\d+) by (\\d+)".r
}

class ShiftRow(y: Int, shift: Int) extends Instruction {
  override def execute(grid: Array[Array[Boolean]]): Unit = {
    val temp = Array.ofDim[Boolean](grid(y).length)

    grid(y).indices.foreach(column => {
      temp(Math.floorMod(column + shift, grid(y).length)) = grid(y)(column)
    })

    temp.indices.foreach(column => grid(y)(column) = temp(column))
  }
}
