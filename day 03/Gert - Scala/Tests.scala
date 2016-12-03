package main.scala.Day3

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    val input = """5 10 25
                  |5 10 14""".stripMargin
    assert(Runner.validateTriangles(input) == 1)
  }

  test("Puzzle 1") {
    val input = scala.io.Source.fromFile("test/main/scala/Day3/puzzle.txt").getLines().toArray
    println(Runner.validateTriangles(input))
  }

  test("Example 2.1") {
    val input = """101 301 501
                  |102 302 502
                  |103 303 503
                  |201 401 601
                  |202 402 602
                  |203 403 603""".stripMargin
    assert(Runner2.validateTriangles(input) == 6)
  }

  test("Puzzle 2") {
    val input = scala.io.Source.fromFile("test/main/scala/Day3/puzzle.txt").getLines().toArray
    println(Runner2.validateTriangles(input))
  }
}
