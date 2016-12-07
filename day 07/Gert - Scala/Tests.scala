package main.scala.Day07

import main.scala.Day07.{Runner, Runner2}
import main.scala.TestBase

import scala.io.Source

class Tests extends TestBase {
  test("Example 1") {
    assert((Runner run
      """abba[mnop]qrst
        |abcd[bddb]xyyx
        |aaaa[qwer]tyui
        |ioxxoj[asdfgh]zxcvbn
      """.stripMargin) == 2)
  }

  test("Puzzle 1") {
    val input = (Source fromFile "test/main/scala/Day07/puzzle.txt").getLines.toArray
    println(Runner run input)
  }

  test("Example 2.1") {
    assert((Runner2 run
      """aba[bab]xyz
        |xyx[xyx]xyx
        |aaa[kek]eke
        |zazbz[bzb]cdb
      """.stripMargin) == 3)
  }

  test("Puzzle 2") {
    val input = (Source fromFile "test/main/scala/Day07/puzzle.txt").getLines.toArray
    println(Runner2 run input)
  }
}