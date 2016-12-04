package main.scala.Day4

object Runner2 extends Runner {
  def realNames(input: Array[String]): Array[String] = {
    input.map(realName)
  }

  def realName(input: String): String = {
    val parsedInput = parseInput(input)
    "%s (%s)".format(decypher(parsedInput.name, parsedInput.sectorId.toInt), parsedInput.sectorId)
  }

  def decypher(name: String, shift: Int): String = {
    name.toCharArray.map(letter => {
      if(letter == '-')
        " "
      else
        ((letter.toInt - 97 + shift) % 26 + 97).toChar
    }).mkString("").trim
  }
}
