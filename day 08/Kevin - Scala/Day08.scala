object Day08 {

  trait Command { def execute(screen: Array[Array[Boolean]]): Array[Array[Boolean]] }

  class RectCommand(x: Int, y: Int) extends Command {
    override def execute(screen: Array[Array[Boolean]]): Array[Array[Boolean]] = {
      (0 until y).foreach(row => { (0 until x).foreach(column => screen(row)(column) = true)})
      screen
    }
  }

  abstract class RotateCommand(index: Int, amount: Int) extends Command {
    override def execute(screen: Array[Array[Boolean]]): Array[Array[Boolean]] = {
      val row = Array.ofDim[Boolean](screen(index).length)
      row.indices.foreach(x => { row(Math.floorMod(x + amount, row.length)) = screen(index)(x) })
      row.indices.foreach(x => screen(index)(x) = row(x))
      screen
    }
  }
  class RotateRowCommand(index: Int, amount: Int) extends RotateCommand (index, amount) {}
  class RotateColumnCommand(index: Int, amount: Int) extends RotateCommand (index, amount) {
    override def execute(screen: Array[Array[Boolean]]): Array[Array[Boolean]] = {
      val transposedScreen = super.execute(screen.transpose)
      transposedScreen.transpose
    }
  }

  val formatRotateRow = """rotate row \w=(\d+) by (\d+)""".r
  val formatRotateColumn = """rotate column \w=(\d+) by (\d+)""".r
  val formatRect = """rect (\d+)x(\d+)""".r

  def parseCommand(command: String): Command = {
    command match {
      case formatRect(x, y) => new RectCommand(x.toInt, y.toInt)
      case formatRotateRow(index, amount) => new RotateRowCommand(index.toInt, amount.toInt)
      case formatRotateColumn(index, amount) => new RotateColumnCommand(index.toInt, amount.toInt)
    }
  }

  def executeCommand(screen: Array[Array[Boolean]], command: Command): Array[Array[Boolean]] = {
    command.execute(screen)
  }

  def solve (commands: Iterator[String]): (Int, Array[Array[Boolean]]) = {
    val commandsParsed = commands.map(parseCommand)
    val processedScreen = commandsParsed.foldLeft(Array.ofDim[Boolean](6, 50))(executeCommand)

    (processedScreen.reduceLeft(_ ++ _).count(_ == true), processedScreen)
  }

  def main(args: Array[String]): Unit = {
    val commands = DataFolder.openFile("day08.txt").getLines()
    val (amountOfTurnedOnLights, screen) = solve(commands)

    println(s"answer: $amountOfTurnedOnLights")

    val testScreen = screen.map(x => x.map(y => {if (y)  "#" else "."}).mkString)
    for (x <- testScreen){
      println(x.mkString)
    }
  }

}
