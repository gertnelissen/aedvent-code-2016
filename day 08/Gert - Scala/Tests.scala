package main.scala.Day08

import main.scala.TestBase

import scala.io.Source

class Tests extends TestBase {
  test("Example 1") {
    val runner = new Runner(3, 7)
    val result = runner.run(
      """rect 3x2
        |rotate column x=1 by 1
        |rotate row y=0 by 4
        |rotate column x=1 by 1
      """.stripMargin)
    runner.print
    assert(result == 6)
  }

  test("Puzzle 1") {
    val input = (Source fromFile "test/main/scala/Day08/puzzle.txt").getLines.toArray
    val runner = new Runner(6, 50)
    println(runner run input)
    runner print
  }
}