package main.scala.Day18

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    assert(Runner.run(5) == 3)
  }

  test("Puzzle 1") {
    println(Runner.run(3014603))
  }

  test("Example 2.1") {
    assert(Runner2.run(5) == 2)
  }

  test("Puzzle 2") {
    println(Runner2.run(3014603)) // takes a long time (Josephus problem), both space and time complexity of algorithm is O(n). Time complexity could probably be less at a cost of space, but space is the limiting factor here. I didn't found anything about an algorithm with lower space complexity...
  }
}