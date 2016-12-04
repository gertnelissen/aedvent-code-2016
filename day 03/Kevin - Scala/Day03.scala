object Day03 {

  def parseDimensions1(dimensionAsString: String): (Int, Int, Int) = {
    var cleanedDimensions = dimensionAsString.trim().split(" ").filter(x => (!x.isEmpty))
    (cleanedDimensions(0).toInt, cleanedDimensions(1).toInt, cleanedDimensions(2).toInt)
  }

  def parseDimensions2(dimensionAsString: String): Array[Int] = {
    var cleanedDimensions = dimensionAsString.trim().split(" ").filter(x => (!x.isEmpty))
    Array(cleanedDimensions(0).toInt, cleanedDimensions(1).toInt, cleanedDimensions(2).toInt)
  }

  def isValidTriangle(x: (Int, Int, Int)): Boolean = {
    (x._1+x._2 > x._3 && x._1+x._3 > x._2 && x._2+x._3 > x._1)
  }

  def isValidTriangle(x: Array[Int]): Boolean = {
    (x(0)+x(1) > x(2) && x(0)+x(2) > x(1) && x(1)+x(2) > x(0))
  }

  def solve(): (Int, Int) = {
    val dimensionsPart1 = DataFolder.openFile("day03.txt").getLines().map(parseDimensions1)
    val dimensionsPart2 = DataFolder.openFile("day03.txt").getLines().toArray.map(parseDimensions2)

    (dimensionsPart1.filter(isValidTriangle _).size,
      dimensionsPart2.transpose.reduceLeft(_ ++ _).grouped(3).filter(isValidTriangle).size)
  }

  def main(args: Array[String]): Unit = {
    val (result1, result2) = solve()
    println(s"answer: $result1")
    println(s"answer: $result2")
  }

}
