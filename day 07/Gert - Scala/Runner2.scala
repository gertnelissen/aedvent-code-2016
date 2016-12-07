package main.scala.Day07

object Runner2 extends Runner {
  override def check(parts: Parts): Boolean = {
    val abas = parts.regularParts flatMap getAbas
    val babs = parts.bracketParts flatMap getAbas

    abas.exists(aba => babs contains "%s%s%s".format(aba.charAt(1), aba.charAt(0), aba.charAt(1)))
  }

  def getAbas(s: String): Array[String] = {
    (0 to s.length - 3)
      .map(position => s.substring(position, position + 3))
      .filter(substr => {
        substr.charAt(0) == substr.charAt(2) &&
          substr.charAt(0) != substr.charAt(1)
      }).toArray
  }
}
