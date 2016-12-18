object Day16 {

  val diskLength = 272
  val diskLengthAdvanced = 35651584
  val initialState = "11100010111110100"

  def main (args: Array[String]): Unit = {
    println(s"Result = ${calculateChecksum(createData(diskLength, initialState), true)}")
    println(s"Result = ${calculateChecksum(createData(diskLengthAdvanced, initialState), true)}")
  }

  def createData(diskLength: Int, input: String): String = {
    if (input.size >= diskLength) input.take(diskLength)
    else createData(diskLength, input + "0" + input.reverse.map(x => if (x == '1') '0' else '1'))
  }

  def calculateChecksum(input: String, firstRun: Boolean): String = {
    if (!firstRun && input.length % 2 != 0) input
    else calculateChecksum(input.grouped(2).map(x => if (x(0).equals(x(1))) "1" else "0").mkString, false)
  }

}
