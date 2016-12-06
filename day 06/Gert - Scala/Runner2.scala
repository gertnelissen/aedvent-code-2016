package main.scala.Day06

object Runner2 extends Runner {
  override def getSpecifiedChar(chars: Array[(Char, Int)]): Char = getLeastOccurringChar(chars)

  def getLeastOccurringChar(chars: Array[(Char, Int)]): Char = {
    chars.groupBy(c => c._1).minBy(t => t._2.length)._1
  }
}