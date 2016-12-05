import java.security.MessageDigest

object Day05 {

  def md5(s: String): Array[Byte] = MessageDigest.getInstance("MD5").digest(s.getBytes)

  def calculateRoomPassword(key: String, index: Int, numberOfZeros: Int): (Int, String) = {
    val hash = md5(key + index).flatMap(byte => Seq(byte >> 4, byte & 0xF))
    if (hash.slice(0, numberOfZeros).forall(_ == 0)) (index, take6th(hash))
    else calculateRoomPassword(key, index + 1, numberOfZeros)
  }

  def take6th(hash: Array[Int]): String = {
     "%x".format(hash(5))
  }

  def solve(): (Int, String) = {
    val puzzleInput: String = "ugkcyxxp"
    (1 to 8).map(x => 1).foldLeft((0, ""))((x, y) => {
      val intermediateResult = calculateRoomPassword(puzzleInput, (x._1 + 1), 5)
      (intermediateResult._1, x._2 ++ intermediateResult._2)
    })
  }

  def main(args: Array[String]): Unit = {
    val (result1, result2) = solve()
    println(s"answer: $result1")
    println(s"answer: $result2")
  }

}
