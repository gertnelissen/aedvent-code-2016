package main.scala.Day5

import scala.util.Try

class Runner2 extends Runner {
  val password = new Array[String](8)

  override def retrievePassword(input: String): String = {
    while(password.contains(null))
      tryFindNextLetter(input)

    password.mkString("")
  }

  def tryFindNextLetter(input: String): Unit = {
    val hash = generateNextHash(input)

    val position = getPosition(hash)
    if(position == Some && password(position.get) == null) {
      val letter = hash.charAt(6).toString
      println("Retrieved position %s: %s".format(position.get, letter))
      password(position.get) = letter.toString
    }
  }

  def getPosition(hash: String): Option[Int] = {
    try {
      val parsed = Try(hash.charAt(5).toString.toInt)

      if (parsed == None || parsed.get < 0 || parsed.get >= 8)
        return None
      return Some(parsed.get)
    }catch {
      case e: Exception => None
    }
  }
}
