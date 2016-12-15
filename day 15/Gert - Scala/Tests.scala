package main.scala.Day15

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    val input = List(
      (5, 4, 1),
      (2, 1, 2)
    )
    assert(Runner.run(input) == 5)
  }

  test("Puzzle 1") {
    val input = List(
      (13, 11, 1),
      (5, 0, 2),
      (17, 11, 3),
      (3, 0, 4),
      (7, 2, 5),
      (19, 17, 6)
    )
    println(Runner.run(input))
  }

  test("Puzzle 2") {
    val input = List(
      (13, 11, 1),
      (5, 0, 2),
      (17, 11, 3),
      (3, 0, 4),
      (7, 2, 5),
      (19, 17, 6),
      (11, 0, 7)
    )
    println(Runner.run(input))
  }
}
