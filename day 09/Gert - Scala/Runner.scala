package main.scala.Day09

object Runner extends Runner {}

trait Runner {
  val pattern = """^(\w*)(\((\d+)x(\d+)\))?(.*)$""".r

  def run(input: String): Long = {
    val pattern(prefix, _, chars, times, remainder) = input
    prefix.length + (
      if(chars != null && times != null)
        replicate(chars.toInt, times.toInt, remainder)
      else
        0
    )
  }

  def replicate(chars: Int, times: Int, remainder: String): Long = {
    remainder.take(chars).length * times + run(remainder.substring(chars))
  }
}