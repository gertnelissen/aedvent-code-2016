import java.security.MessageDigest

object Passwords2 extends App{
  val digest = MessageDigest.getInstance("MD5")

  Util.readStdIn.foreach(
     calculatePassword
  )
  def calculatePassword(input:String) = {
    println(getNextChar(input,0,Array('g','g','g','g','g','g','g','g')))
  }

  def getNextChar(input:String,integer:Int,appendPw: Array[Char]) :String = {
    val md5hash1 = digest.digest((input+integer).getBytes).map("%02x".format(_)).mkString
    (md5hash1,appendPw) match {
      case (_,pw) if !pw.contains('g') == 8 => pw.mkString("")
      case (hash,pw) if(hash.startsWith("00000")) => {
        val pos = hash.charAt(5)
        if(pos.isDigit && pos.asDigit < 8 && pw(pos.asDigit) == 'g'){
          pw(pos.asDigit) = hash.charAt(6)
          println(pw.toList)
        }
        getNextChar(input,integer+1,pw)
      }
      case (hash,pw) => getNextChar(input,integer+1,pw)
    }
  }

}
