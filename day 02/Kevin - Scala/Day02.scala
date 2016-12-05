object Day02 {

  def getDirectionCoordinates(directions: String): List[(Int, Int)] = {
    directions.map{
      case 'U' => (0, 1)
      case 'R' => (1, 0)
      case 'L' => (-1, 0)
      case 'D' => (0, -1)
    }.toList
  }

  def getCodeFromDirectionCoordinates1(start: (Int, Int), directionCoordinates: List[(Int, Int)]): (Int, Int) = {
    directionCoordinates.scanLeft(start){case ((a,b), (c,d)) => nextButtonHelper1(a,b,c,d)}.last
  }

  def nextButtonHelper1(a: Int, b: Int, c: Int, d: Int): (Int, Int) = {
    if (-1 <= a+c && a+c <=1 && -1 <= b+d && b+d <= 1) (a+c, b+d)
    else (a, b)
  }

  def getCodeFromDirectionCoordinates2(start: (Int, Int), directionCoordinates: List[(Int, Int)]): (Int, Int) = {
    directionCoordinates.scanLeft(start){case ((a,b), (c,d)) => nextButtonHelper2(a,b,c,d)}.last
  }

  def nextButtonHelper2(a: Int, b: Int, c: Int, d: Int): (Int, Int) = {
    if (-1 <= a+c && a+c <=1 && -1 <= b+d && b+d <= 1){
      (a+c, b+d)
    }
    else {
      if ( (a+c == 0 && -2 <= b+d && b+d <= 2) || (b+d == 0 && -2 <= a+c && a+c <= 2) ){
        (a+c, b+d)
      }
      else (a, b)
    }
  }

  def solve(): (Iterator[(Int, Int)], Iterator[(Int, Int)]) = {
    val directionCoordinates = DataFolder.openFile("day02.txt").getLines().map(getDirectionCoordinates)
    (directionCoordinates.scanLeft((0, 0))(getCodeFromDirectionCoordinates1),
      directionCoordinates.scanLeft((-2, 0))(getCodeFromDirectionCoordinates2))
  }

  def main(args: Array[String]): Unit = {
    val (result1, result2) = solve()
    println(s"answer: $result1")
    println(s"answer: $result2")
    for (x <- result2) println(x)
  }

}
