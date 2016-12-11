object IPv72 extends App{
  var total = 0
  Util.readAllLines.foreach(supportsSSL)
  println(total)

  def supportsSSL(input:String): Unit ={
    var inHyperNet = false
    var babsInHyperNet = Array[String]()
    var abasInSuperNet = Array[String]()
    var ssl = false
    input.sliding(3).foreach(lexNextThreeTokens)
    if(ssl){
      total += 1
    }

    def lexNextThreeTokens(x : String) = x match {
      case `x` if x.head == '[' => inHyperNet = true
      case `x` if x.head == ']' => inHyperNet = false
      case `x` if isAba(x) && !inHyperNet && !babsInHyperNet.contains(bab(x)) => abasInSuperNet = abasInSuperNet :+ x
      case `x` if isAba(x) && !inHyperNet && babsInHyperNet.contains(bab(x)) => ssl = true
      case `x` if isAba(x) && inHyperNet && !abasInSuperNet.contains(bab(x)) => babsInHyperNet = babsInHyperNet :+ x
      case `x` if isAba(x) && inHyperNet && abasInSuperNet.contains(bab(x)) => ssl = true
      case _ =>
    }
  }

  def isAba(token : String): Boolean = token.head == token.last && token.head != token.charAt(1)
  def bab(aba : String) : String = aba.charAt(1).toString + aba.head.toString + aba.charAt(1).toString

}
