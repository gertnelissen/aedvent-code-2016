package main.scala.Day13

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    Runner.run((9, 6), (4, 7), 10)
  }

  test("Puzzle 1") {
    Runner.run((50, 50), (39, 31), 1352)
  }
}
