import scala.util.matching.Regex

object RealRooms2 extends App{
  val sectorRE = ".*-([0-9]+).*".r
  val nameRE = "(.*)-[0-9].*".r

  Util.readAllLines.foreach(decodeRoom)

  def decodeRoom(encryptedRoom:String): Unit ={
    val nameRE(name) = encryptedRoom
    val sectorRE(sector) = encryptedRoom
    val decoded = name.map {
      case '-' => ' '
      case other if((other.toInt + (sector.toInt % 26)) <= 122) => (other.toInt + (sector.toInt % 26)).toChar
      case other => (other.toInt + (sector.toInt % 26) - 26).toChar
    }
    println(decoded + '/' + sector)
  }
}