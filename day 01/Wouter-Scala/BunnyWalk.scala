object BunnyWalk extends App {
    Util.readStdIn.foreach( input => {
        solve(input.split(", ").toList)
    })

    def solve(directions: List[String]) = {
      val gps = new GPS
        directions.foreach(
          direction => gps.run(direction.charAt(0),direction.substring(1).toInt)
        )
      println(gps.getDistance)
    }
}
class GPS() {
  var currentDirection = 'U'
  var coords = (0, 0)
  val directions = List[Char]('U', 'R', 'D', 'L')

  def run(direction: Char, amount: Int) = direction match {
    case 'R' => right(currentDirection,amount)
    case 'L' => left(currentDirection,amount)
    case _ => println("Bogus input")
  }

  def left(direction: Char, amount: Int) = {
    currentDirection = directions.sliding(2).collectFirst { case List(previous, `direction`) => previous }.getOrElse('L')
    updateCoords(amount)
  }

  def right(direction: Char, amount: Int) = {
    currentDirection = directions.sliding(2).collectFirst { case List(`direction`, next) => next }.getOrElse('U')
    updateCoords(amount)
  }

  def getDistance : Int = Math.abs(coords._1) + Math.abs(coords._2)

  def updateCoords(amount: Int): Unit = currentDirection match {
    case 'U' => coords = (coords._1, coords._2 + amount)
    case 'R' => coords = (coords._1 + amount, coords._2)
    case 'D' => coords = (coords._1, coords._2 - amount)
    case 'L' => coords = (coords._1 - amount, coords._2)
    case _ => println("Derp")
  }
}



