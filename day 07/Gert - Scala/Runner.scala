package main.scala.Day07

case class Parts(regularParts: Array[String], bracketParts: Array[String])

object Runner extends Runner {}

class Runner {
  def run(input: String): Int = {
    run(input split "\r\n")
  }

  def run(input: Array[String]): Int = {
    input map toParts map check count (_ == true)
  }

  def toParts(input: String): Parts = {
    val parts = input.trim.replace("[", "|%").replace("]", "|")
      .split('|').filter(!_.isEmpty)

    val regularParts = parts filter (_.charAt(0) != '%')
    val bracketParts = parts filter (_.charAt(0) == '%')

    new Parts(regularParts, bracketParts)
  }

  def check(parts: Parts): Boolean = {
    (parts.regularParts exists checkPart) &&
      !(parts.bracketParts exists checkPart)
  }

  def checkPart(input: String): Boolean = {
    (0 to input.length - 4) exists (position => {
      val left = input.substring(position, position + 2)
      val right = input.substring(position + 2, position + 4)

      left.charAt(0) != left.charAt(1) &&
        left.charAt(0) == right.charAt(1) &&
        left.charAt(1) == right.charAt(0)
    })
  }
}