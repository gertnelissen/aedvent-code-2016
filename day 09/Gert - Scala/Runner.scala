package main.scala.Day09

object Runner extends Runner {}

class Runner {
  val pattern = """^(\w*)(\((\d+)x(\d+)\))?(.*)$""".r

  def run(input: String): Long = {
    val (prefix, replicator, remainder) = parse(input)

    if(replicator != null) {
      prefix.length + replicate(replicator, remainder)
    } else
      prefix.length
  }

  def parse(input: String): (String, (Int, Int), String) = {
    val pattern(prefix, group, chars, times, remainder) = input

    val replicator =
      if(chars == null || times == null) null
      else (chars.toInt, times.toInt)

    (prefix, replicator, remainder)
  }

  def replicate(replicator: (Int, Int), remainder: String): Long = {
    val toReplicate = remainder.take(replicator._1)
    val rest = remainder.substring(replicator._1)

    (toReplicate.length * replicator._2) + run(rest)
  }
}