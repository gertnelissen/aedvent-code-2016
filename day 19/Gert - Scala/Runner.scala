package main.scala.Day18

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

object Runner2 extends Runner {
  override def run(numElfs: Int): Int = {
    val group = Range(1, numElfs + 1).toBuffer
    var player = 0

    while(group.length > 1) {
      val oppositePosition = (player + (group.length / 2)) % group.length
      group.remove(oppositePosition)
      player = (if(player > oppositePosition) player else player + 1) % group.length
    }

    group.toList.head
  }
}