package main.scala.Day17

import main.scala.TestBase

import scala.io.Source

class Tests extends TestBase {
  test("Example 1") {
    assert(Runner.run("ihgpwlah") == "DDRRRD")
  }

  test("Example 2") {
    assert(Runner.run("kglvqrro") == "DDUDRLRRUDRD")
  }

  test("Example 3") {
    assert(Runner.run("ulqzkmiv") == "DRURDRUDDLLDLUURRDULRLDUUDDDRR") // RDDRLDRURD
  }

  test("Puzzle 1") {
    println(Runner.run("qzthpkfp"))
  }

  test("Example 2.1") {
    assert(Runner2.run("ihgpwlah") == "370")
  }

  test("Example 2.2") {
    assert(Runner2.run("kglvqrro") == "492")
  }

  test("Example 2.3") {
    assert(Runner2.run("ulqzkmiv") == "830")
  }

  test("Puzzle 2") {
    println(Runner2.run("qzthpkfp")) // 448 in 2.5s
  }
}