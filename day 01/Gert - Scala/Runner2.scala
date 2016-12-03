package main.scala.Day1

import scala.collection.mutable.ListBuffer

class Runner2 extends Runner {
  val history = new ListBuffer[(Int, Int)]()
  var stop = false

  override def takeStep() {
    if(stop)
      return

    recordPosition()
    super.takeStep()

    stop = beenHereBefore()
  }

  def recordPosition(): Unit = {
    history += ((position.x, position.y))
  }

  def beenHereBefore(): Boolean = {
    history.contains((position.x, position.y))
  }
}