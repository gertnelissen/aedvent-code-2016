package main.scala.Day06

object Runner extends Runner {}

class Runner {
  def run(input: String): String = {
    run(input.split("\r\n"))
  }

  def run(input: Array[String]): String = {
    val mostFrequentLettersPerPosition = input
      .flatMap(entry => entry.zipWithIndex)
      .groupBy(_._2)
      .map(t => (t._1, getSpecifiedChar(t._2)))

    createPassword(mostFrequentLettersPerPosition)
  }

  def getSpecifiedChar(chars: Array[(Char, Int)]): Char = getMostOccurringChar(chars)

  def getMostOccurringChar(chars: Array[(Char, Int)]): Char = {
    chars.groupBy(c => c._1).maxBy(t => t._2.length)._1
  }

  def createPassword(letters: Map[Int, Char]): String = {
    letters.toArray.sortBy(entry => entry._1).map(_._2).mkString("")
  }
}
