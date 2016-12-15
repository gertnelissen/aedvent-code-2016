package main.scala.Day12

object Runner extends Runner {}

class Runner {
  val patterns = new {
    val copyValue = """cpy (-?\d+) (\w+)""".r
    val copyRegister = """cpy (\w+) (\w+)""".r
    val jumpValue = """jnz (-?\d+) (-?\d+)""".r
    val jumpRegister = """jnz (\w+) (-?\d+)""".r
    val increment = """inc (\w+)""".r
    val decrement = """dec (\w+)""".r
  }

  abstract class Instruction {
    def execute(environment: Environment): Environment
  }

  abstract class Copy extends Instruction {}

  class CopyValue(val value: Int, val to: String) extends Copy {
    override def execute(environment: (Int, Program, Registers)): Environment = {
      val updatedRegister = environment._3.map(register => {
        if(register._1 == to)
          (register._1, value)
        else
          register
      })

      (environment._1 + 1, environment._2, updatedRegister)
    }
  }

  class CopyRegister(val from: String, val to: String) extends Copy {
    override def execute(environment: (Int, Program, Registers)): Environment = {
      val updatedRegister = environment._3.map(register => {
        if(register._1 == to)
          (register._1, environment._3.get(from).get)
        else
          register
      })

      (environment._1 + 1, environment._2, updatedRegister)
    }
  }

  abstract class Jump extends Instruction {}

  class JumpValue(val value: Int, val jump: Int) extends Jump {
    override def execute(environment: (Int, Program, Registers)): Environment = {
      if(value != 0)
        (environment._1 + jump, environment._2, environment._3)
      else
        (environment._1 + 1, environment._2, environment._3)
    }
  }

  class JumpRegister(val register: String, val jump: Int) extends Copy {
    override def execute(environment: (Int, Program, Registers)): Environment = {
      if(environment._3.get(register).get != 0)
        (environment._1 + jump, environment._2, environment._3)
      else
        (environment._1 + 1, environment._2, environment._3)
    }
  }

  class Increment(val register: String) extends Copy {
    override def execute(environment: (Int, Program, Registers)): Environment = {
      val updatedRegister = environment._3.map(r => {
        if(r._1 == register)
          (r._1, r._2 + 1)
        else
          r
      })

      (environment._1 + 1, environment._2, updatedRegister)
    }
  }

  class Decrement(val register: String) extends Copy {
    override def execute(environment: (Int, Program, Registers)): Environment = {
      val updatedRegister = environment._3.map(r => {
        if(r._1 == register)
          (r._1, r._2 - 1)
        else
          r
      })

      (environment._1 + 1, environment._2, updatedRegister)
    }}


  type Program = Array[Instruction]
  type Registers = Map[String, Int]
  type Environment = (Int, Program, Registers)

  def run(input: String): Option[Int] = {
    val program = parseProgram(input)
    val defaultRegisters = Map("a" -> 0, "b" -> 0, "c" -> 1, "d" -> 0)

    var environment = (0, program, defaultRegisters)

    while(true) {
      // check if program pointer is out of bounds
      if(stopProgram(environment))
        return environment._3.get("a")

      environment = execute(environment)
    }

    None
  }

  def execute(environment: Environment): Environment = {
    val nextInstruction = environment._2(environment._1)
    //    println("Executing line %s: %s on environment:%s".format(environment._1, nextInstruction, environment._3))
    nextInstruction.execute(environment)
  }

  def stopProgram(environment: Environment) : Boolean = {
    environment._1 < 0 || environment._1 >= environment._2.length
  }

  def parseProgram(input: String): Program = {
    input.split("\r\n").map(parseInstruction)
  }

  def parseInstruction(input: String): Instruction = {
    input match {
      case patterns.copyValue(value, register) => new CopyValue(value.toInt, register)
      case patterns.copyRegister(from, to) => new CopyRegister(from, to)
      case patterns.jumpValue(value, jump) => new JumpValue(value.toInt, jump.toInt)
      case patterns.jumpRegister(register, jump) => new JumpRegister(register, jump.toInt)
      case patterns.increment(register) => new Increment(register)
      case patterns.decrement(register) => new Decrement(register)
      case _ => throw new IllegalArgumentException("Couldn't parse instruction %s".format(input))
    }
  }
}
