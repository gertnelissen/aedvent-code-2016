package main.scala.Day10

import main.scala.TestBase

import scala.io.Source

class Tests extends TestBase {
  test("Example 1") {
    val input = """value 5 goes to bot 2
                  |bot 2 gives low to bot 1 and high to bot 0
                  |value 3 goes to bot 1
                  |bot 1 gives low to output 1 and high to bot 0
                  |bot 0 gives low to output 2 and high to output 0
                  |value 2 goes to bot 2""".stripMargin
    assert(Runner.run(input, List(5, 2)).contains(2))
  }

  test("Puzzle 1") {
    val input = Source.fromFile("test/main/scala/Day10/puzzle.txt").getLines().mkString("\r\n")
    println(Runner.run(input, List(61, 17)).mkString(", "))
  }

  test("Example 2.1") {
    val input = """value 5 goes to bot 2
                  |bot 2 gives low to bot 1 and high to bot 0
                  |value 3 goes to bot 1
                  |bot 1 gives low to output 1 and high to bot 0
                  |bot 0 gives low to output 2 and high to output 0
                  |value 2 goes to bot 2""".stripMargin
    assert(Runner2.run(input, List(0, 1, 2)).contains(30))
  }

  test("Puzzle 2") {
    val input = Source.fromFile("test/main/scala/Day10/puzzle.txt").getLines().mkString("\r\n")
    println(Runner2.run(input, List(0, 1, 2)).mkString(", "))
  }
}
