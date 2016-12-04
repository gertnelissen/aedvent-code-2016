object Day04 {

  val format = """(\D+)(\d+)\[(\w+)\]""".r

  def parse(encryptedLine: String): (String, Int, String) = {
    val format(words, sectorId, checksum) = encryptedLine
    (words.split("-").reduceLeft(_ ++ _), sectorId.toInt, checksum)
  }

  def createStringOf5MostOccuringChars(x: (String, Int, String)): (String, Int, String) = {
    val stringOf5MostOccuringChars = x._1.toCharArray().groupBy(identity)
                      .map{case (letter, list) => (letter, list.size)}.toSeq
                      .sortWith((first, second) => {
                        if (first._2 == second._2){
                          first._1 < second._1
                        } else first._2 > second._2
                      })
                      .take(5).map(_._1).mkString
    (stringOf5MostOccuringChars, x._2, x._3)
  }

  def isValidChecksum(parsedLine: (String, Int, String)): Boolean = {
    (parsedLine._1).equals(parsedLine._3)
  }

  def decypherRoomName(parsedLine: (String, Int, String)): (String, Int) = {
    val decypheredName = parsedLine._1.toCharArray
                                   .map(x => (((x.toInt - 97 + parsedLine._2) % 26) + 97).toChar)
                                   .mkString
    (decypheredName, parsedLine._2)
  }

  def solve(): (Int, Iterator[(String, Int)]) = {
    val parsedLines = DataFolder.openFile("day04.txt").getLines().map(parse)
    val parsedLines2 = DataFolder.openFile("day04.txt").getLines().map(parse)

    (parsedLines.map(createStringOf5MostOccuringChars(_)).filter(isValidChecksum(_)).map(_._2).sum,
      parsedLines2.map(decypherRoomName))
  }

  def main(args: Array[String]): Unit = {
    val (result1, result2) = solve()
    println(s"answer: $result1")
    for (x <- result2) println(x)
  }

}
