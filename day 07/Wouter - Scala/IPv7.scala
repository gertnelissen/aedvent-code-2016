object IPv7 extends App{
  var total = 0
  Util.readAllLines.foreach(supportsTLS)
  println(total)

  def supportsTLS(input:String): Unit ={
    var inHyperNet = false
    var check = 0
    input.sliding(4).foreach {
      case x if(x.head == '[') => inHyperNet = true
      case x if(x.head == ']') => inHyperNet = false
      case x if(check >= 0 && isAbba(x) && inHyperNet) => check = -1
      case x if(check >=0 && isAbba(x) && !inHyperNet) => check = 1
      case _ =>
    }
    if(check == 1){
      total += 1
    }
  }

  def isAbba(token : String): Boolean = token.head == token.last && token.charAt(1) == token.charAt(2) && token.head != token.charAt(1)
}
