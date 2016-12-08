package main.scala.Day08

import scala.util.matching.Regex

trait InstructionCompanion {
  val pattern: Regex
}

trait Instruction {
  def execute(grid: Array[Array[Boolean]])
}
