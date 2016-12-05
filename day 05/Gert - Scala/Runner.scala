package main.scala.Day5

import java.security.MessageDigest

class Runner {
  val passwordLength = 8
  var seed = 0L

  def retrievePassword(input: String): String = {
    (1 to passwordLength).map(_ => generateNextHash(input).charAt(5)).mkString("")
  }

  def generateNextHash(input: String): String = {
    var hash = ""

    while (hash.take(5) != "00000") {
      hash = md5(input + seed.toString)
      seed += 1
    }

    //    println("Got hash %s on seed %s".format(hash, seed - 1))
    hash
  }

  def md5(s: String): String = {
    MessageDigest.getInstance("MD5").digest(s.getBytes).map("%02X".format(_)).mkString("")
  }
}


