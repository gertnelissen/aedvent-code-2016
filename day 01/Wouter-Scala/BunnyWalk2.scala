object BunnyWalk2 extends App {
  Util.readStdIn.foreach( input => {
    solve(input.split(", ").toList)
  })

  def solve(directions: List[String]) = {
    val gps = new FancyGPS
    directions.foreach(
        direction =>
          if(!gps.revisisted){
            gps.run(direction.charAt(0), direction.substring(1).toInt)
          }
    )
    println(gps.getDistance)
  }
}
class FancyGPS() {
  var currentDirection = 'U'
  var coords = (0, 0)
  var revisisted = false
  var history = List(coords)
  val directions = List[Char]('U', 'R', 'D', 'L')

  def run(direction: Char, amount: Int) ={
      direction match {
        case 'R' => right(currentDirection, amount)
        case 'L' => left(currentDirection, amount)
        case _ => println("Bogus input")
      }
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

  def updateCoords(amount: Int) = currentDirection match {
    case 'U' => coords = walk(amount, (x,y) => (n) => (x,y+n) )
    case 'R' => coords = walk(amount, (x,y) => (n) => (x+n,y) )
    case 'D' => coords = walk(amount, (x,y) => (n) => (x,y-n) )
    case 'L' => coords = walk(amount, (x,y) => (n) => (x-n,y) )
    case _ => println("Derp")
  }

  def walk(amount:Int, f:  (Int,Int) => (Int) => (Int,Int)) : (Int, Int) ={
    amount match {
      case 0 => coords
      case _ => {
          coords = f(coords._1,coords._2)(1)
          if(history.contains(coords)){
            revisisted = true
            return coords
          }
          history ++= List(coords)
          walk(amount -1,f)
        }
    }
  }
}