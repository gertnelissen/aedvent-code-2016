object Day13 {

  val officeDesignerNumber = 1358
  val validMoves = Seq((0, 1), (0, -1), (1, 0), (-1, 0))
  var currentLeastNumberOfSteps = Int.MaxValue

  def tryNextSteps(takenSteps: Int, position: (Int, Int), move: (Int, Int), visited: Seq[(Int, Int)]): Int = {
    val (newX, newY) = (position._1 + move._1, position._2 + move._2)
    if (newX == 31 && newY == 39){
      if (currentLeastNumberOfSteps > takenSteps + 1) currentLeastNumberOfSteps = takenSteps + 1
      takenSteps + 1
    }
    else {
      if (isValidPosition(newX, newY) && takenSteps + 1 < currentLeastNumberOfSteps && ! visited.contains((newX, newY))){
        validMoves.filter(x => !(x._1 == move._1 * -1 && x._2 == move._2 * -1))
                  .map(tryNextSteps(takenSteps + 1, (newX, newY), _, visited :+ (newX, newY)))
                  .min
      }
      else Int.MaxValue
    }
  }

  def isValidPosition(x: Int, y: Int): Boolean =
    x >= 0 && y >= 0 && isOpenSpace(x, y)

  def isOpenSpace(x: Int, y: Int): Boolean =
    (x*x + 3*x + 2*x*y + y + y*y + officeDesignerNumber)
      .toBinaryString.toLong.toString.toCharArray.count(_ == '1') % 2 == 0

  def main(args: Array[String]): Unit = {
    val fewestStepsRequired = validMoves.map(tryNextSteps(0, (1, 1), _, Seq[(Int, Int)]())).min
    println(s"answer: $fewestStepsRequired")
  }

}
