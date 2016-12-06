package main.scala.Day06

import main.scala.TestBase

import scala.io.Source

class Day6 extends TestBase {
  test("Example 0") {
    val input = """abcde
                  |abcfg
                  |hijfg""".stripMargin
    assert(Runner.run(input) == "abcfg")
  }

  test("Example 1") {
    val input = """eedadn
                  |drvtee
                  |eandsr
                  |raavrd
                  |atevrs
                  |tsrnev
                  |sdttsa
                  |rasrtv
                  |nssdts
                  |ntnada
                  |svetve
                  |tesnvt
                  |vntsnd
                  |vrdear
                  |dvrsen
                  |enarar""".stripMargin
    assert(Runner.run(input) == "easter")
  }

  test("Puzzle 1") {
    val input = Source.fromFile("test/main/scala/Day06/puzzle.txt").getLines().toArray
    println(Runner.run(input))
  }

  test("Example 2.1") {
    val input = """eedadn
                  |drvtee
                  |eandsr
                  |raavrd
                  |atevrs
                  |tsrnev
                  |sdttsa
                  |rasrtv
                  |nssdts
                  |ntnada
                  |svetve
                  |tesnvt
                  |vntsnd
                  |vrdear
                  |dvrsen
                  |enarar""".stripMargin
    assert(Runner2.run(input) == "advent")
  }

  test("Puzzle 2") {
    val input = Source.fromFile("test/main/scala/Day06/puzzle.txt").getLines().toArray
    println(Runner2.run(input))
  }
}