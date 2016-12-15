package main.scala.Day14

import java.security.MessageDigest


object Runner extends Runner {}

class Runner {
  val tripletPattern = """.*(.)\1\1.*""".r
  val fiveletPattern = """.*(.)\1\1\1\1.*""".r

//  var keys = List[Int]()
//  var tripletChars = Map[Int, Char]()
//
//  var hashes = Map[Int, String]()

  var triplets = Map[Int, Char]()
  var keys = List[Int]()

  /*
  def run(seed: String, numKeys: Int, hashMethod: (String, Int) => String): Unit = {
    Stream.from(0).foreach(i => {
      val hash = hashMethod(seed, i)

      val tripletChar = getTripletChar(hash)
      if(tripletChar.isDefined) {
        if(isKeyChar(seed, i, tripletChar.get, hashMethod)) {
          keys = i :: keys
          println("Found key %s, %s in total".format(i, keys.length))
        }
      }

      if(keys.length >= numKeys) {
        println("Keys (%s): %s".format(keys.length, keys.sorted))
        println("Key #%s: %s".format(numKeys, keys.sorted.take(numKeys).reverse.head))
        return
      }
    })
  }
  */

  def isKeyChar(seed: String, i: Int, c: Char, hashMethod: (String, Int) => String): Boolean = {
    (i + 1 to i + 1000).foreach(j => {
      val jHash = hashMethod(seed, j)
      if(containsFivelet(jHash, c))
        return true
    })
    return false
  }

  def run(seed: String, numKeys: Int, hashMethod: (String, Int) => String): Unit = {
    var iterationToRunTo = -1

    Stream.from(0).foreach(i => {
      val hash = hashMethod(seed, i)

      // handle fivelet
      val fivelet = getFiveletChar(hash)
      if (fivelet.isDefined) {
        // search triplets for same char
        val matchingTriplets = triplets.filter(triplet => triplet._1 < i && triplet._1 >= i - 1000)
          .filter(triplet => triplet._2 == fivelet.get)
        val foundKeys = matchingTriplets.keys.toList
        keys = keys.union(foundKeys).distinct

        if(foundKeys.nonEmpty)
          println("Found keys %s, %s total".format(foundKeys, keys.length))
      }

      // handle triplet
      val triplet = getTripletChar(hash)
      if (triplet.isDefined) {
        triplets += i -> triplet.get
      }

      // handle finish check
      if (keys.length >= numKeys && iterationToRunTo == -1)
        iterationToRunTo = keys.sorted.reverse.head + 1000

      if(i == iterationToRunTo) {
        println("Keys (%s): %s".format(keys.length, keys))
        println("Keys (%s): %s".format(keys.length, keys.sorted))
        val lastKey = keys.sorted.take(numKeys).reverse.head
        println("Key #%s: %s".format(numKeys, lastKey))
        return
      }
    })
  }

  def getTripletChar(s: String): Option[Char] = {
//    s match {
//      case tripletPattern(c) => Some(c.charAt(0))
//      case _ => None
//    }

    (0 to s.length - 3).foreach(i => {
      val substr = s.substring(i, i + 3)
      if(substr.charAt(0) == substr.charAt(1) && substr.charAt(0) == substr.charAt(2))
        return Some(substr.charAt(0))
    })

    None
  }

  def getFiveletChar(s: String): Option[Char] = {
//    s match {
//      case fiveletPattern(c) => Some(c.charAt(0))
//      case _ => None
//    }

    (0 to s.length - 5).foreach(i => {
      val substr = s.substring(i, i + 5)
      if(substr.charAt(0) == substr.charAt(1) && substr.charAt(0) == substr.charAt(2)
        && substr.charAt(0) == substr.charAt(3) && substr.charAt(0) == substr.charAt(4))
        return Some(substr.charAt(0))
    })

    None
  }

  def findOccurences(tripletChars: Map[Int, Char], char: Char): List[Int] = {
    tripletChars.filter(c => c._2 == char).keys.toList
  }

  def containsFivelet(s: String, c: Char): Boolean = {
    val fivelet = (1 to 5).map(_ => c).mkString("")
    s.contains(fivelet)
  }

  def getHash(prefix: String, iteration: Int): String = {
    val input = "%s%s".format(prefix, iteration.toString)
    md5(input)
  }

  def getStretchedHash(prefix: String, iteration: Int): String = {
    val input = "%s%s".format(prefix, iteration.toString)
    var hash = input
    (0 to 2016).foreach(i => {
      hash = md5(hash)
    })
    hash
  }

  def md5(s: String): String = {
    MessageDigest.getInstance("MD5").digest(s.getBytes)
      .map("%02X".format(_)).mkString("").toLowerCase.replace("-", "")
  }
}
