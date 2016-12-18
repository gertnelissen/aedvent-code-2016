package main.scala.Day16

import main.scala.TestBase

import scala.io.Source

class Tests extends TestBase {
  test("Example 1") {
    assert(Runner.run("110010110100", 12) == "100")
  }

  test("Example 2") {
    assert(Runner.run("10000", 20) == "01100")
  }

  test("Puzzle 1") {
    println(Runner.run("01111010110010011", 272))
  }

  test("Puzzle 2") {
    println(Runner.run("01111010110010011", 35651584)) // 40 seconds
  }
}
