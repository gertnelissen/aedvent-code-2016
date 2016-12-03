package main.scala.Day1

class Runner() {
  var directions = Array("up", "right", "down", "left")
  var direction = 0
  var position = new {
    var x = 0
    var y = 0
  }

  def run(instructions: String): Unit = {
    instructions.split(", ").foreach(followInstruction)
  }

  def followInstruction(instruction: String): Unit = {
    rotate(instruction.take(1))
    takeSteps(instruction.substring(1).toInt)
  }

  def rotate(rotation: String): Unit = {
    if(rotation == "R")
      direction = Math.floorMod(direction + 1, 4)
    else if (rotation == "L")
      direction = Math.floorMod(direction - 1, 4)
  }

  def takeSteps(steps: Int): Unit = {
    (1 to steps).foreach(_ => takeStep())
  }

  def takeStep(): Unit = {
    if(directions(direction) == "up")
      position.y += 1
    else if(directions(direction) == "down")
      position.y -= 1
    else if(directions(direction) == "right")
      position.x += 1
    else if(directions(direction) == "left")
      position.x -= 1
  }

  def distance(): Int = {
    Math.abs(position.x) + Math.abs(position.y)
  }
}