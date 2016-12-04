package main.scala.Day4

case class Input(name: String, sectorId: String, checkSum: String)

object Runner extends Runner

class Runner {
  val inputPattern = """(\D+)(\d+)\[(\w+)\]""".r

  def sumRealRoomIds(input: String): Int = {
    sumRealRoomIds(input.split("\n").map(_.trim).filter(!_.isEmpty))
  }

  def sumRealRoomIds(input: Array[String]): Int = {
    input.map(parseInput(_)).filter(isReal).map(_.sectorId.toInt).sum
  }

  def isReal(input: String): Boolean = isReal(parseInput(input))

  def isReal(input: Input): Boolean = {
    analyze(input.name) == input.checkSum
  }

  def parseInput(input: String): Input ={
    val inputPattern(name, sectorId, checkSum) = input
    new Input(name, sectorId, checkSum)
  }

  def analyze(s: String): String = {
    s.toCharArray.filter(_ != '-')
      .groupBy[Char](letter => letter)
      .map{case (letter, occurrences) => (letter, occurrences.length)}.toSeq
      .sortWith((a, b) => {
        if(a._2 == b._2)
          a._1 < b._1
        else
          a._2 > b._2
      })
      .take(5)
      .map(_._1)
      .mkString("")
  }
}
