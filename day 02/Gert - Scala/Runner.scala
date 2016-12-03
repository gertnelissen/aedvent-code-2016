package main.scala.Day2

import scala.collection.mutable.ListBuffer

class Runner {
  var keyPad = Array(
    Array("1", "2", "3"),
    Array("4", "5", "6"),
    Array("7", "8", "9")
  )

  var position = new {
    var x = 1
    var y = 1
  }

  var keysPressed = new ListBuffer[String]

  def code(): String = keysPressed.mkString("")

  def run(instructions: String): Unit = {
    instructions.split("\n")foreach(instructionSet => {
      followInstructionSet(instructionSet)
      keysPressed += keyPad(position.x)(position.y)
    })
  }

  def followInstructionSet(instructionSet: String): Unit = {
    instructionSet.foreach(followInstruction(_))
  }

  def followInstruction(instruction: Char): Unit = {
    if(instruction == 'U' && isValidPosition(position.x-1, position.y))
      position.x -= 1
    else if(instruction == 'D' && isValidPosition(position.x+1, position.y))
      position.x += 1
    else if(instruction == 'L' && isValidPosition(position.x, position.y-1))
      position.y -= 1
    else if(instruction == 'R' && isValidPosition(position.x, position.y+1))
      position.y += 1
  }

  def isValidPosition(x: Int, y: Int): Boolean = {
    x >= 0 && y >= 0 &&
      keyPad.length > x &&
      keyPad(x).length > y &&
      keyPad(x)(y) != null
  }
}