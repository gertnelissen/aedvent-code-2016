package main.scala.Day5

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    assert(new Runner().retrievePassword("abc").toLowerCase == "18f47a30")
  }

  test("Puzzle 1") {
    println(new Runner().retrievePassword("cxdnnyjw"))
  }

  test("Example 2.1") {
    assert(new Runner2().retrievePassword("abc").toLowerCase == "05ace8e3")
  }

  test("Puzzle 2") {
    println(new Runner2().retrievePassword("cxdnnyjw"))
  }
}