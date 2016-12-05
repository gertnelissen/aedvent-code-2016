object Triangles extends App{

var counter = 0
  Util.readStdIn.foreach(
    triangleLine => {
      check(parse(triangleLine))
      println(counter)
    }
  )

  def parse(triangleLine: String) : List[Int] = {
    triangleLine.trim.replaceAll(" +", ",").split(",").map(string => string.toInt).toList
  }

  def check(sides: List[Int]) = {
    sides match {
      case x :: y :: z :: Nil => if(x+y > z && y+z > x && x+z > y){counter+=1}
    }
  }

}
