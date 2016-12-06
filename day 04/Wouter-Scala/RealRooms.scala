object RealRooms extends App{
  var sum = 0
  val checkSumRE = ".*\\[([a-z]+)\\]".r
  val sectorRE = ".*-([0-9]+).*".r
  val nameRE = "(.*)-[0-9].*".r

  Util.readAllLines.foreach(checkIfRealRoom)
  println(sum)

  def checkIfRealRoom(encryptedName:String) : Unit = {
    var nameRE(name) = encryptedName
    val checkSumRE(checksum) = encryptedName
    name = name.replaceAll("-","")
    if(checksum == countOccurrences(name.distinct,name).take(5).foldLeft[String]("")((x,y) => x+y._1)){
      val sectorRE(sector) = encryptedName
      sum += sector.toInt
    }
  }

  def countOccurrences(chars:String,original:String): Array[(Char, Int)] ={
    var occurrences = Array[(Char, Int)]()
    chars.foreach( char => {
      occurrences = occurrences :+ (char,original.count(_ == char)*(-1))
    })
    occurrences.sortBy( r => (r._2,r._1))
  }
}