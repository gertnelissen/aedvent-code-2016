package main.scala.Day14

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    new Runner().run("abc", 64, Runner.getHash)
    // 22728
  }

  test("Puzzle 1") {
    new Runner().run("qzyelonm", 64, Runner.getHash)
    // 15168
  }

  test("Example 2.1") {
    Runner.run("abc", 64, Runner.getStretchedHash)
    // 22859 => my computation was one-off though...
  }

  test("Puzzle 2") {
    Runner.run("qzyelonm", 64, Runner.getStretchedHash)
    // 20864
  }
}
