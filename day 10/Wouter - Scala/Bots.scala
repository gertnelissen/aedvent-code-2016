import scala.collection.mutable.Queue

object Bots extends App{
  val inputRE = "value (\\d+) goes to (bot \\d+)".r
  val giveRE = "(bot \\d+) gives low to (bot \\d+|output \\d+) and high to (bot \\d+|output \\d+)".r

  Util.readAllLines.foreach(parseInstruction)
  def parseInstruction(input:String) = input match {
    case inputRE(chip,bot) => BotManager.initialGivings = BotManager.initialGivings :+ (chip.toInt,bot)
    case giveRE(actor,lowTO,highTo) => BotManager.addInstructionToBot(actor,lowTO,highTo)
    case  _ => println("Bad regex")
  }
  BotManager.handout()
  println("Product is %d".format(BotManager.outputs("output 0").chips.product * BotManager.outputs("output 1").chips.product * BotManager.outputs("output 2").chips.product))
}


object BotManager{
  var bots = Map[String,Bot]()
  var outputs = Map[String,Output]()
  var initialGivings = Array[(Int,String)]()

  def handout() = {
    initialGivings.foreach( params => {
      giveChipToBot(params._1,params._2)
    })
  }

  def giveChipTo(chip:Int, entity:String) : Unit={
      if(entity.startsWith("bot")) giveChipToBot(chip,entity)
      if(entity.startsWith("output")) giveChipToOutput(chip,entity)
  }

  def giveChipToBot(chip:Int, bot:String) = {
    getBot(bot).giveChip(chip)
  }

  def addInstructionToBot(bot: String, lowTo: String, highTo: String)={
    getBot(bot).addInstruction(Instruction(lowTo,highTo))
  }

  def giveChipToOutput(chip:Int, output:String) = {
    outputs.getOrElse(output,{
      val bin =new Output(output)
      outputs = outputs + (output -> bin)
      bin
    }).addChip(chip)
  }

  def getBot(bot:String) : Bot = {
    bots.getOrElse(bot,{
      val r2d2 =new Bot(bot)
      bots = bots + (bot -> r2d2)
      r2d2
    })
  }

}

class Bot(name: String){
  var chips = Array[Int]()
  val instructions = Queue[Instruction]()

  def addInstruction(instruction: Instruction) = instructions.enqueue(instruction)

  def giveChip(chip: Int) = {
    if(chips.length == 0){
      chips = chips :+ chip
    } else {
      chips = chips :+ chip
      if(instructions.nonEmpty){
        chips = chips.sorted
        if (chips.contains(17) && chips.contains(61)) {
          println("%s is the winner".format(name))
        }
        val instruction = instructions.dequeue
        BotManager.giveChipTo(chips(0), instruction.lowTo)
        BotManager.giveChipTo(chips(1), instruction.highTo)
        chips = Array[Int](chip)
      }
    }
  }

}

class Output(name: String) {
  var chips = Array[Int]()
  def addChip(chip: Int) = {
    chips = chips :+ chip
  }
}

case class Instruction(lowTo: String, highTo: String)