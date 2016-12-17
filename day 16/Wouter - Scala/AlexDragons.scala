object AlexDragons extends App{

  calculate(35651584, Array(true, false, false, false, true, true, true, false, false, true, true, true, true, false, false, false, false))

  def calculate(discLen: Int, input: Array[Boolean]) {
    val t0 = System.nanoTime()
    var fill = data(input)
    while (fill.length < discLen) {
      fill = data(fill)
    }
    val sum = checksum(fill.take(discLen))
    println("The checksum is %s".format(toString(sum)))
    val t1 = System.nanoTime()
    println(t1 - t0 + " ns")
  }

  def toString(sum: Array[Boolean]) : String = {
    var result = ""
    sum.foldLeft("")((s,b) => if(b) s+"1" else s+"0")
  }

  def data(a : Array[Boolean]) : Array[Boolean] = {
    val b = new Array[Boolean](a.length)
    var i = b.length/2
    while( i >= 0) {
      val opp = b.length - 1 - i
      b(i) = !a(opp)
      b(opp) = !a(i)
      i -= 1
    }
    if (a.length%2 != 0) {
      b(a.length/2) = !a(a.length/2)
    }
    //return append(a, append([]bool{false}, b...)...)
    a ++ List(false) ++ b
  }

  def checksum(a: Array[Boolean]) : Array[Boolean] = {
    val sum = new Array[Boolean](a.length/2)
    for (i <- a.indices by 2){
      sum(i/2) = a(i) == a(i + 1)
    }
    if (sum.length % 2 != 0) {
      sum
    } else {
      checksum(sum)
    }
  }

}
