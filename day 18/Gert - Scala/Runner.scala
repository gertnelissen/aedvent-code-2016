package main.scala.Day18

object Runner extends Runner {}

class Runner {
  def run(firstRow: String, numLines: Int): Int = {
    val tiles = createStream(firstRow).take(numLines)
    tiles.map(_.count(c => c == '.')).sum
  }

  def createStream(previous: String): Stream[String] = {
    previous #:: createStream(nextRow(previous))
  }

  def nextRow(previous: String): String = {
    previous.zipWithIndex.map(tile => {
      val tilesToConsider = previous.substring(
        Math.max(tile._2 - 1, 0),
        Math.min(tile._2 + 2, previous.length))

      val trapTiles = tilesToConsider.count(t => t == '^')
      val centerIsTrap = previous.charAt(tile._2) == '^'

      if((trapTiles == 2 && centerIsTrap) || (trapTiles == 1 && !centerIsTrap))
        '^'
      else
        '.'
    }).mkString("")
  }
}