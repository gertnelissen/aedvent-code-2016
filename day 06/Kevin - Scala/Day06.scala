object Day06 {

  def charArrayToMostOccuringChar(charArray: Array[Char]): Seq[(Char, Int)] = {
    charArray.groupBy(identity)
             .map{case (letter, list) => (letter, list.size)}.toSeq
             .sortWith((first, second) => {
                if (first._2 == second._2){
                  first._1 < second._1
                } else first._2 > second._2
             })
  }

  def main(args: Array[String]): Unit = {
    val messages = DataFolder.openFile("day06.txt").getLines().map(_.toCharArray).toArray
    val sortedMessages = messages.transpose.map(charArrayToMostOccuringChar(_))

    val result1 = sortedMessages.map(x => x.head._1).mkString
    val result2 = sortedMessages.map(x => x.last._1).mkString

    println(s"answer: $result1")
    println(s"answer: $result2")
  }

}
