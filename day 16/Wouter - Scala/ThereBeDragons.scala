object ThereBeDragons extends App{

  println(calcCheckSum(randomData("10001001100000001",35651584)))

  def calcCheckSum(randomData:String) :String ={
    randomData.sliding(2,2).foldLeft("")((checkSum,bits) => checkSum + getNextCheckSumChar(bits)) match {
      case checksum if isEven(checksum.length) => calcCheckSum(checksum)
      case checksum => checksum
    }
  }

  def getNextCheckSumChar(bits:String) = bits match {
    case "00" => "1"
    case "11" => "1"
    case "01" => "0"
    case "10" => "0"
  }

  def randomData(initialData:String,diskSize:Int) : String = (initialData,diskSize) match {
    case (data, size) if data.length >= size => data.take(size)
    case (data, size) => randomData(data + "0" + invert(data.reverse), size)
  }

  def invert(data:String) : String = data.map( c => if(c == '0') '1' else '0')
  def isEven(number: Int) = number % 2 == 0

}
