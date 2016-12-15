package main.scala.Day11

import main.scala.Day11.Runner.{Config, Floor}
import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    val input = Config(1, Array(
      Floor(1, 0, 2),
      Floor(2, 1, 0),
      Floor(3, 1, 0),
      Floor(4, 0, 0)),
      0)
    Runner.run(input) // 11
  }

  test("Puzzle 1") {
    val input = Config(1, Array(
      Floor(1, 5, 3),
      Floor(2, 0, 2),
      Floor(3, 0, 0),
      Floor(4, 0, 0)),
      0)
    Runner.run(input) // 47 after 1s
  }

  test("Puzzle 2") {
    val input = Config(1, Array(
      Floor(1, 7, 5),
      Floor(2, 0, 2),
      Floor(3, 0, 0),
      Floor(4, 0, 0)),
      0)
    Runner.run(input) // 71 after a fat second!
  }
}