object Day12 {

  val copyInstruction = """cpy (\w+) (\w+)""".r
  val incrementInstruction = """inc (\w+)""".r
  val decrementInstruction = """dec (\w+)""".r
  val jumpInstruction = """jnz (\w+) (-)?(\d+)""".r

  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }

  def executeInstructions(state: (Map[String, Int], Int), instructions: Seq[String]): Int = {
    val (variables, pointer) = state

    println(instructions(pointer) + "---- b = " + variables("b"))

    val (newVariables, newPointer) = instructions(pointer) match {

      case copyInstruction(value, variable) =>
        toInt(value) match {
          case Some(integerValue) => (variables.updated(variable, integerValue), pointer + 1)
          case None => (variables.updated(variable, variables(value)), pointer + 1)
        }

      case incrementInstruction(variable) => (variables.updated(variable, variables(variable) + 1), pointer + 1)
      case decrementInstruction(variable) => (variables.updated(variable, variables(variable) -1), pointer + 1)
      case jumpInstruction(variable, sign, amount) =>
        toInt(variable) match {
          case Some(integerValue) =>
            if (integerValue != 0)
              if (sign != null) (variables, pointer - amount.toInt)
              else (variables, pointer + amount.toInt)
            else (variables, pointer + 1)
          case None =>
            if (variables(variable) != 0)
              if (sign != null) (variables, pointer - amount.toInt)
              else (variables, pointer + amount.toInt)
            else (variables, pointer + 1)
        }
    }

    if (newPointer >= instructions.size) newVariables("a")
    else executeInstructions((newVariables, newPointer), instructions)
  }

  def solve(instructions: Seq[String]): Int = {
    executeInstructions((Map[String, Int]().withDefaultValue(0), 0), instructions)
  }

  def solve2(instructions: Seq[String]): Int = {
    val initialVariables = Map[String, Int]().withDefaultValue(0).updated("c", 1)
    executeInstructions((initialVariables, 0), instructions)
  }

  def main(args: Array[String]): Unit = {
    val instructions = DataFolder.openFile("day12.txt").getLines.toSeq
    val valueForA1 = solve(instructions)
    val valueForA2 = solve2(instructions)

    println(s"answer: $valueForA1")
    println(s"answer: $valueForA2")
  }

}
