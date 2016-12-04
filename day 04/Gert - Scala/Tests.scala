package main.scala.Day4

import main.scala.TestBase

class Tests extends TestBase {
  test("Example 1") {
    assert(Runner.isReal("aaaaa-bbb-z-y-x-123[abxyz]"))
  }

  test("Example 2") {
    assert(Runner.isReal("a-b-c-d-e-f-g-h-987[abcde]"))
  }

  test("Example 3") {
    assert(Runner.isReal("not-a-real-room-404[oarel]"))
  }

  test("Example 4") {
    assert(Runner.isReal("totally-real-room-200[decoy]") == false)
  }

  test("Count real rooms") {
    val input = """aaaaa-bbb-z-y-x-123[abxyz]
                  |a-b-c-d-e-f-g-h-987[abcde]
                  |not-a-real-room-404[oarel]
                  |totally-real-room-200[decoy]""".stripMargin
    assert(Runner.sumRealRoomIds(input) == 1514)
  }

  test("Puzzle 1") {
    val input = scala.io.Source.fromFile("test/main/scala/Day4/puzzle.txt").getLines().toArray
    println(Runner.sumRealRoomIds(input))
  }

  test("Example 2.1") {
    assert(Runner2.realName("qzmt-zixmtkozy-ivhz-343[xxxxx]") == "very encrypted name")
  }

  test("Puzzle 2") {
    val input = scala.io.Source.fromFile("test/main/scala/Day4/puzzle.txt").getLines().toArray
    println(Runner2.realNames(input)
      .filter(realName => realName.contains("north") && realName.contains("pole"))
      .mkString("\n"))
  }
}
