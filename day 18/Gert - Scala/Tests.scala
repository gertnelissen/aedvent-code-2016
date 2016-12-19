package main.scala.Day18

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    assert(Runner.run("..^^.", 3) == 6)
  }

  test("Example 2") {
    assert(Runner.run(".^^.^.^^^^", 10) == 38)
  }

  test("Puzzle 1") {
    println(Runner.run("^.....^.^^^^^.^..^^.^.......^^..^^^..^^^^..^.^^.^.^....^^...^^.^^.^...^^.^^^^..^^.....^.^...^.^.^^.^", 40)) // 1974
  }

  test("Puzzle 2") {
    println(Runner.run("^.....^.^^^^^.^..^^.^.......^^..^^^..^^^^..^.^^.^.^....^^...^^.^^.^...^^.^^^^..^^.....^.^...^.^.^^.^", 400000)) // 19991126 in 10 seconds
  }
}
