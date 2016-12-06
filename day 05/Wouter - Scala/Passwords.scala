import java.security.MessageDigest

object Passwords extends App{
  val digest = MessageDigest.getInstance("MD5")

  Util.readStdIn.foreach(
     calculatePassword
  )
  def calculatePassword(input:String) = {
    println(getNextChar(input,0,""))
  }

  def getNextChar(input:String,integer:Int,appendPw: String) :String = {
    val md5hash1 = digest.digest((input+integer).getBytes).map("%02x".format(_)).mkString
    (md5hash1,appendPw) match {
      case (_,pw) if pw.length == 8 => pw
      case (hash,pw) if(hash.startsWith("00000")) => {
        println("Found one!")
        getNextChar(input,integer+1,pw+hash.charAt(5))
      }
      case (hash,pw) => getNextChar(input,integer+1,pw)
    }
  }

}
