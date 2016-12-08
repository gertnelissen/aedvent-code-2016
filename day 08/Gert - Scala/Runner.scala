package main.scala.Day08

class Runner(rows: Int, columns:Int) {
  val grid = Array.ofDim[Boolean](rows, columns)

  def run(input: String): Int = {
    run(input.split("\r\n").filter(!_.trim.isEmpty))
  }

  def run(input: Array[String]): Int = {
    input map parseInstruction foreach executeInstruction
    grid.map(row => row.count(_ == true)).sum
  }

  def parseInstruction(instruction: String): Instruction = {
    instruction match {
      case Rect.pattern(x, y) => new Rect(x.toInt, y.toInt)
      case ShiftColumn.pattern(x, shift) => new ShiftColumn(x.toInt, shift.toInt)
      case ShiftRow.pattern(y, shift) => new ShiftRow(y.toInt, shift.toInt)
      case _ => throw new IllegalArgumentException("Can't parse instruction %s".format(instruction))
    }
  }

  def executeInstruction(instruction: Instruction): Unit = {
    instruction.execute(grid)
  }

  def print(): Unit = {
    println(grid.map(row => row.map(cell => if(cell) '#' else '.').mkString("")).mkString("\n"))
  }
}