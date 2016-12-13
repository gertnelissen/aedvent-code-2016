object Monorail2 extends App{
  var a, b, d, pointer = 0
  var c = 1
  val cpyRE = "cpy (\\d+|.) (.)".r
  val incRE = "inc (.)".r
  val decRE = "dec (.)".r
  val jnzRE = "jnz (\\d+|.) (-{0,1}\\d+)".r
  var instructions = Vector[Instruction]()

  Util.readAllLines.foreach(parseInstructions)
  while(instructions.length > pointer){
    instructions(pointer).execute()
  }
  println(get("a"))

  def parseInstructions(input:String) = input match {
    case cpyRE(value, to) => instructions = instructions :+ Copy(value,to)
    case incRE(to) => instructions = instructions :+ Increase(to)
    case decRE(to) => instructions = instructions :+ Decrease(to)
    case jnzRE(value, amount) => instructions = instructions :+ Jump(value,amount)
    case _ => println("No regex matches!")
  }

  trait Instruction{
    def execute()
  }

  case class Copy(value:String, to:String) extends Instruction {
    override def execute(): Unit = {
      if(isAllDigits(value)) update(to,value.toInt)
      else update(to,get(value))
      pointer += 1
    }
  }

  case class Increase(to:String) extends Instruction {
    override def execute(): Unit = {
      update(to,get(to)+1)
      pointer += 1
    }
  }

  case class Decrease(to:String) extends Instruction {
    override def execute(): Unit = {
      update(to,get(to)-1)
      pointer += 1
    }
  }

  case class Jump(value:String, amount:String) extends Instruction {
    override def execute(): Unit = {
      if(isAllDigits(value) && value.toInt != 0) pointer += amount.toInt
      else if(get(value) != 0) pointer += amount.toInt
      else pointer += 1
    }
  }

  def isAllDigits(x: String) = x forall isDigitChar
  def isDigitChar(in:Char) = Character.isDigit(in) || in == '-'

  def update(register:String,value:Int): Unit = register match {
    case "a" => a = value
    case "b" => b = value
    case "c" => c = value
    case "d" => d = value
    case _ => println("Ünkown register!")
  }

  def get(register:String): Int = register match {
    case "a" => a
    case "b" => b
    case "c" => c
    case "d" => d
    case _ =>
      println("Ünkown register!")
      -10
  }

}
