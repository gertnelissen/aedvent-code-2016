object Triangles2 extends App{
  var counter = 0
  Util.readAllLines.sliding(3,3).toList.foreach( listOfThree => {
    var list1, list2, list3 = List[Int]()
    listOfThree.map(parse).foreach {
      case List(x, y, z) => {
        list1 = list1 :+ x
        list2 = list2 :+ y
        list3 = list3 :+ z
      }
    }
    check(list1)
    check(list2)
    check(list3)
  })
  println(counter)

  def parse(triangleLine: String) : List[Int] = {
    triangleLine.trim.replaceAll(" +", ",").split(",").map(string => string.toInt).toList
  }

  def check(sides: List[Int]) = {
    sides match {
      case x :: y :: z :: Nil => if(x+y > z && y+z > x && x+z > y){counter+=1}
    }
  }


}
