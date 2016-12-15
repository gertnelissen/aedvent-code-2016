object Day13 {

  val officeDesignerNumber = 1358
  val validMoves = Seq((0, 1), (0, -1), (1, 0), (-1, 0))

  def tryNextSteps(takenSteps: Int, position: (Int, Int), move: (Int, Int), visited: Set[(Int, Int)]): Set[(Int, Int)] = {
    val (newX, newY) = (position._1 + move._1, position._2 + move._2)
    if (isValidPosition(newX, newY) && takenSteps < 50 && ! visited.contains((newX, newY))){
      validMoves.filter(x => !(x._1 == move._1 * -1 && x._2 == move._2 * -1))
                .map(tryNextSteps(takenSteps + 1, (newX, newY), _, visited + position))
                .reduceLeft(_ ++ _)
    }
    else visited + position
  }

  def isValidPosition(x: Int, y: Int): Boolean =
    x >= 0 && y >= 0 && isOpenSpace(x, y)

  def isOpenSpace(x: Int, y: Int): Boolean =
    (x*x + 3*x + 2*x*y + y + y*y + officeDesignerNumber)
      .toBinaryString.toLong.toString.toCharArray.count(_ == '1') % 2 == 0

  def main(args: Array[String]): Unit = {
    val numberOfPlaces = validMoves.map(tryNextSteps(0, (1, 1), _, Set[(Int, Int)]())).reduceLeft(_ ++ _).size
    println(s"answer: $numberOfPlaces")
  }

}
