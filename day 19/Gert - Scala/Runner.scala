package main.scala.Day19

object Runner extends Runner {}

class Runner {
  def run(numElfs: Int): Int = {
    val group  = Range(1, numElfs + 1).toList
    Iterator.iterate(group)(reduce).find(_.length == 1).head.head
  }

  def reduce(group: List[Int]): List[Int] = {
    group.grouped(2).map(_(0)).drop(group.length % 2).toList
  }
}