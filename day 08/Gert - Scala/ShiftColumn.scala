package main.scala.Day08
import scala.util.matching.Regex

object ShiftColumn extends InstructionCompanion {
  override val pattern: Regex = "rotate column x=(\\d+) by (\\d+)".r
}

class ShiftColumn(x: Int, shift: Int) extends Instruction {
  override def execute(grid: Array[Array[Boolean]]): Unit = {
    val temp = Array.ofDim[Boolean](grid.length)

    grid.indices.foreach(row => {
      temp(Math.floorMod(row + shift, grid.length)) = grid(row)(x)
    })

    temp.indices.foreach(row => grid(row)(x) = temp(row))
  }
}
