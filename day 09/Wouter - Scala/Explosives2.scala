import scala.collection.mutable.ArrayBuffer

//This algorithm is off about 4k wtf?

object Explosives2 extends App{
  val markerRE = "\\((\\d+)x(\\d+)\\)".r
  val markerStartRE = "^\\((\\d+)x(\\d+)\\)(.*)".r
  var size = BigInt(0)
  var markers = ArrayBuffer[(String,Int)]()
  Util.readStdIn.foreach(calcSize)

  def calcSize(input:String) = {
    size = BigInt(input.length)
    markerRE.findAllIn(input).foreach(marker => markers = markers :+ (marker,1))
    decompressRecursive(input)
    println(size)
  }

  def decompressRecursive(input: String) : Unit = {
    input match {
      case "" =>
      case _ if markers.isEmpty =>
      case markerStart if input.startsWith(markers(0)._1) =>
        val currentMarker = markers.remove(0)
        val markerRE(scopeStr,multiplierStr) = currentMarker._1
        calcNewSize(currentMarker,scopeStr.toInt,multiplierStr.toInt)
        val rest = markerStart.drop(currentMarker._1.length)
        updateNextMarkers(rest.take(scopeStr.toInt),multiplierStr.toInt)
        decompressRecursive(rest)
      case rest => decompressRecursive(input.tail)
    }
  }

  def updateNextMarkers(searchScope:String, multiplier : Int) = {
    var index = 0
    var tmpSearchScope = searchScope
    while(markers.length > index && searchScope.contains(markers(index)._1)){
      tmpSearchScope = tmpSearchScope.replaceFirst(markers(index)._1,"")
      markers(index) = (markers(index)._1,markers(index)._2 * multiplier)
      index += 1
    }
  }

  def calcNewSize(marker : (String,Int), scope:Int, multiplier:Int) = {
    size += ((BigInt(scope.toInt) * (BigInt(multiplier.toInt) - BigInt(1)))-BigInt(marker._1.length)) * BigInt(marker._2)
  }

}
