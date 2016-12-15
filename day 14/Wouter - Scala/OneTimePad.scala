import java.security.MessageDigest
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object OneTimePad extends App{

  var hashes = new java.util.concurrent.ConcurrentHashMap[Int,String]()
  val totalSearches = 50000
  val threeRE = "(.)\\1{2,}".r


  Util.readStdIn.foreach(doStuff)

  def doStuff(input: String) = {
    multiThreadedHashGeneration(input)
    multiThreadedChecks()

  }

  def multiThreadedHashGeneration(input:String) = {
    val hashCalcs = for (i <- 0 to totalSearches) yield Future {
        addHash(i,input+i)
    }
    val boxedFuture = Future.sequence(hashCalcs)
    Await.result(boxedFuture,15.seconds)
  }

  def addHash(index: Int, toHash: String): String ={
    val digest = MessageDigest.getInstance("MD5")
    hashes.put(index, digest.digest(toHash.getBytes).map("%02x".format(_)).mkString)
  }

  def multiThreadedChecks() = {
    val checks = for (i <- 0 to totalSearches - 1000) yield Future {
      check(i)
    }
    val boxed: Future[Seq[Int]] = Future.sequence(checks)
    val results = Await.result(boxed, 100.seconds).toSet.toVector.sorted
    println(results(64))
  }

  def check(index: Int) : Int = {
    val fives = threeRE.findFirstIn(hashes.get(index))
    fives match {
      case Some(threes) => startSearching(threes.head.toString*5,index)
      case None => -1
    }
  }

  def startSearching(toSearch: String, index : Int) : Int = {
    var searchIndex = index+1
    while (searchIndex < index + 1000) {
      if (hashes.get(searchIndex).contains(toSearch)) {
        return index
      }
      searchIndex += 1
    }
    -1
  }

}
