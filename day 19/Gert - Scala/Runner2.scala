package main.scala.Day19

object Runner2 extends Runner {
  override def run(numElfs: Int): Int = {
    val group = Range(1, numElfs + 1).toBuffer
    var playerPosition = 0

    while(group.length > 1) {
      val oppositePosition = (playerPosition + (group.length / 2)) % group.length
      group.remove(oppositePosition)
      playerPosition = (if(playerPosition > oppositePosition) playerPosition else playerPosition + 1) % group.length
    }

    group.toList.head
  }
}