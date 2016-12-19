package main.scala.Day16

object Runner extends Runner {}

class Runner {
  def run(input: String, diskLength: Int): String = {
    val code = Iterator.iterate(input)(generate).find(_.length >= diskLength).get
    Iterator.iterate(code.take(diskLength))(checksum).find(_.length % 2 == 1).get
  }

  def generate(previous: String): String = {
    "%s0%s".format(previous, flip(previous.reverse))
  }

  def flip(code: String): String = {
    code.replace("0", "x").replace("1", "0").replace("x", "1")
  }

  def checksum(code: String): String = {
    code.sliding(2, 2).map(s => if(s.charAt(0) == s.charAt(1)) "1" else "0").mkString("")
  }
}