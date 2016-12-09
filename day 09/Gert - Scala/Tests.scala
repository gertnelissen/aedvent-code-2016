package main.scala.Day09

import main.scala.TestBase
import scala.io.Source

class Tests extends TestBase {
  test("Example 1") {
    assert(Runner.run("ADVENT") == "ADVENT".length)
  }

  test("Example 2") {
    assert(Runner.run("A(1x5)BC") == "ABBBBBC".length)
  }

  test("Example 3") {
    assert(Runner.run("(3x3)XYZ") == "XYZXYZXYZ".length)
  }

  test("Example 4") {
    assert(Runner.run("A(2x2)BCD(2x2)EFG") == "ABCBCDEFEFG".length)
  }

  test("Example 5") {
    assert(Runner.run("(6x1)(1x3)A") == "(1x3)A".length)
  }

  test("Example 6") {
    assert(Runner.run("X(8x2)(3x3)ABCY") == "X(3x3)ABC(3x3)ABCY".length)
  }

  test("Puzzle 1") {
    val input = Source.fromFile("test/main/scala/Day09/puzzle.txt").getLines().toArray
    println(Runner.run(input(0)))
  }

  test("Example 2.1") {
    assert(Runner2.run("(3x3)XYZ") == "XYZXYZXYZ".length)
  }

  test("Example 2.2") {
    assert(Runner2.run("X(8x2)(3x3)ABCY") == "XABCABCABCABCABCABCY".length)
  }

  test("Example 2.3") {
    assert(Runner2.run("(27x12)(20x12)(13x14)(7x10)(1x12)A") == 241920)
  }

  test("Example 2.4") {
    assert(Runner2.run("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN") == 445)
  }

  test("Puzzle 2") {
    val input = Source.fromFile("test/main/scala/Day09/puzzle.txt").getLines().toArray
    println(Runner2.run(input(0)))
  }
}