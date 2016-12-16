object ThereBeDragons2 extends App{
  val t0 = System.nanoTime()
  val rd = randomData("10001001100000001",35651584)

  println(calcCheckSum(rd.sliding(2,2).foldLeft(Vector[Int]())((v,bits) => v :+ bits.toInt)))

  val t1 = System.nanoTime()
  println(t1 - t0 + " ns")

  def calcCheckSum(randomData:Vector[Int]) :String ={
    randomData.map(getNextCheckSumBit) match {
      case v if !isEven(v.length) => v.foldLeft("")((t,i) => t + i)
      case v => calcCheckSum(v.sliding(2,2).foldLeft(Vector[Int]())((v,bits) => v:+ appendBit(bits(0),bits(1))))
    }
  }

  def getNextCheckSumBit(bits:Int) = bits match {
    case 11 => 1
    case 0 => 1
    case 1 => 0
    case 10 => 0
  }

  def appendBit(bit1: Int, bit2: Int)= (bit1,bit2) match {
      case (0,0) => 0
      case (0,1) => 1
      case (1,0) => 10
      case (1,1) => 11
  }

  def randomData(initialData:String,diskSize:Int) : String = (initialData,diskSize) match {
    case (data, size) if data.length >= size => data.take(size)
    case (data, size) => randomData(data + "0" + invert(data.reverse), size)
  }

  def invert(data:String) : String = data.map( c => if(c == '0') '1' else '0')
  def isEven(number: Int) = number % 2 == 0
}