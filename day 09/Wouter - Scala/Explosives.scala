object Explosives extends App{
  val markerRE = "^\\((\\d+)x(\\d+)\\)(.*)".r
  Util.readStdIn.foreach(decompress)

  def decompress(input: String) = {
    println(decompressRecursive(input,"").size)
  }

  def decompressRecursive(input: String, output: String) : String = {
    input match {
      case "" => output
      case markerRE(nrLetters,multiplyBy,rest) => decompressRecursive(rest.drop(nrLetters.toInt),output+(rest.take(nrLetters.toInt)*multiplyBy.toInt))
      case rest => decompressRecursive(input.tail,output + input.head)
    }
  }
}
