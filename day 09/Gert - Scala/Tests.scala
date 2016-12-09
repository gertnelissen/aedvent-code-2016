package main.scala.Day09

import main.scala.TestBase

import scala.io.Source

class Tests extends TestBase {
  test("Example 1") {
    val result = Runner.run("ADVENT")
    assert(result == "ADVENT".length)
  }

  test("Example 2") {
    val result = Runner.run("A(1x5)BC")
    assert(result == "ABBBBBC".length)
  }

  test("Example 3") {
    val result = Runner.run("(3x3)XYZ")
    assert(result == "XYZXYZXYZ".length)
  }

  test("Example 4") {
    val result = Runner.run("A(2x2)BCD(2x2)EFG")
    assert(result == "ABCBCDEFEFG".length)
  }

  test("Example 5") {
    val result = Runner.run("(6x1)(1x3)A")
    assert(result == "(1x3)A".length)
  }

  test("Example 6") {
    val result = Runner.run("X(8x2)(3x3)ABCY")
    assert(result == "X(3x3)ABC(3x3)ABCY".length)
  }

  test("Puzzle 1") {
    val input = Source.fromFile("test/main/scala/Day09/puzzle.txt").getLines().toArray
    val result = Runner.run(input(0))
    println(result)
  }

  test("Example 2.1") {
    val result = Runner2.run("(3x3)XYZ")
    assert(result == "XYZXYZXYZ".length)
  }

  test("Example 2.2") {
    val result = Runner2.run("X(8x2)(3x3)ABCY")
    assert(result == "XABCABCABCABCABCABCY".length)
  }

  test("Example 2.3") {
    val result = Runner2.run("(27x12)(20x12)(13x14)(7x10)(1x12)A")
    assert(result == 241920)
  }

  test("Example 2.4") {
    val result = Runner2.run("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")
    assert(result == 445)
  }

  test("Puzzle 2") {
    val input = Source.fromFile("test/main/scala/Day09/puzzle.txt").getLines().toArray
    val result = Runner2.run(input(0))
    println(result)
  }
}