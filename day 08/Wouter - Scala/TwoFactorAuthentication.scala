object TwoFactorAuthentication extends App {
  var points = Set[List[Int]]()
  val maxWidth = 50
  val maxHeight = 6
  val rectRE = "rect\\s(\\d+)x(\\d+).*".r
  val rotateColumnRE = "rotate\\scolumn\\sx=(\\d+)\\sby\\s(\\d+).*".r
  val rotateRowRE = "rotate\\srow\\sy=(\\d+)\\sby\\s(\\d+).*".r

  Util.readAllLines.foreach {
    case rectRE(width,height) => addRect(width.toInt,height.toInt)
    case rotateColumnRE(colNr,amount) => rotateCol(colNr.toInt,amount.toInt)
    case rotateRowRE(rowNr,amount) => rotateRow(rowNr.toInt,amount.toInt)
    case x => println("some random instruction" + x)
  }
  println(points.size)

  def addRect(width: Int, height: Int) = {
    points ++= fillSquare(width,height)
  }

  def rotateCol(colNr:Int, amount:Int) = {
    val existingPoints = points & column(colNr)
    points --= existingPoints
    points ++= transformCol(existingPoints, amount)
  }

  def rotateRow(rowNr:Int, amount:Int) = {
    val existingPoints = points & row(rowNr)
    points --= existingPoints
    points ++= transformRow(existingPoints, amount)
  }

  def transformCol(col: Set[List[Int]], amount: Int): Set[List[Int]] = {
    col.map{case x :: y :: Nil => List(x,transformInStraightLine(y,amount,maxHeight))}
  }

  def transformRow(row: Set[List[Int]], amount: Int): Set[List[Int]] = {
    row.map{case x :: y :: Nil => List(transformInStraightLine(x,amount,maxWidth),y)}
  }

  def transformInStraightLine(current : Int,amount: Int, max:Int) = {
    val moveNr = (amount % max) + current
    if(moveNr < max) moveNr
    else moveNr - max
  }

  def max(x: Int, y: Int) = if(x > y) x else y
  def fillSquare(width: Int, height: Int) = {
    val permList = List(0 until max(width, height):_*)
    prod(permList).filter {
      case (x :: y :: Nil) if x < width && y < height => true
      case _ => false
    } toList
  }
  def prod[Int](lst: List[Int]) = List.fill(2)(lst).flatten.combinations(2).flatMap(_.permutations)

  def column(nr: Int) : Set[List[Int]] = (for {x <- nr to nr; y <- 0 to maxHeight } yield List(x, y)) toSet
  def row(nr: Int) : Set[List[Int]] = (for {x <- 0 to maxWidth; y <- nr to nr } yield List(x, y)) toSet
}