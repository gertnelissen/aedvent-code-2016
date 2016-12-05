import java.security.MessageDigest
import scala.util.{Try, Success, Failure}

object Day05 {

  def md5(s: String): Array[Byte] = MessageDigest.getInstance("MD5").digest(s.getBytes)

  def calculateRoomPassword(key: String, index: Int, numberOfZeros: Int, roomPassword: Array[Char]): (Int, Array[Char]) = {
    val hash = md5(key + index).flatMap(byte => Seq(byte >> 4, byte & 0xF))
    if (hash.slice(0, numberOfZeros).forall(_ == 0)) (index, tryToFillRoomPassword(hash, roomPassword))
    else calculateRoomPassword(key, index + 1, numberOfZeros, roomPassword)
  }

  def tryToFillRoomPassword(hash: Array[Int], roomPassword: Array[Char]): Array[Char] = {
     val position = Try("%x".format(hash(5)).toInt)
     position match {
       case Success(v) => {
         if (0 <= v && v <= 7 && roomPassword(v) == '-'){
           println(roomPassword.mkString)
           roomPassword.patch(v, "%x".format((hash(6) + 16) % 16), 1)
         }
         else roomPassword
       }
       case Failure(v) => roomPassword
     }
  }

  def solve(): String = {
    val puzzleInput: String = "ugkcyxxp"
    var roomPassword: Array[Char] = Array('-', '-', '-', '-', '-', '-', '-', '-')

    var index = 0;

    while (roomPassword.contains('-')){
      val (index1, roomPassword1) = calculateRoomPassword(puzzleInput, index, 5, roomPassword)
      index = index1 + 1
      roomPassword = roomPassword1
    }

    roomPassword.mkString
  }

  def main(args: Array[String]): Unit = {
    val result = solve()
    println(s"answer: $result")
  }

}
