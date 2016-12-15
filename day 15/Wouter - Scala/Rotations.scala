object Rotations extends App{
  val inputRE = ".*Disc #(\\d+) has (\\d+).*position (\\d+).*".r
  val searchScope = 150000

  val discs : List[Disc] = Util.readAllLines.map[Disc,Array[Disc]]{
    case inputRE(nr,positions,position) => Disc(nr.toInt,positions.toInt,position.toInt)
  }.toList

  val baseTimesTable : List[List[Int]] = convertToBaseTimeTable(discs.map(getTimesAtWhichDiscIsAtPosition0))

  def getTimesAtWhichDiscIsAtPosition0(disc: Disc) : List[Int] = (for(i <- disc.positions - disc.position to searchScope by disc.positions ) yield i).toList
  def convertToBaseTimeTable(timesTable: List[List[Int]]) : List[List[Int]] = (for(index <- timesTable.indices) yield subtractPosition(timesTable(index),index+1)).toList
  def subtractPosition(times:List[Int],positionInList:Int) : List[Int] = times.map(t => t - positionInList)
  def largestDisc(discs:List[Disc]) : Disc = discs.sortBy(_.positions).last

  println(findSolution(baseTimesTable,baseTimesTable(discs.indexOf(largestDisc(discs)))))

  def findSolution(baseTimeTable: List[List[Int]],largestDiscBaseTimes: List[Int]) : Int = largestDiscBaseTimes match {
    case Nil => -1
    case x :: tail if eachListContains(baseTimeTable,x) => x
    case x :: tail => findSolution(baseTimeTable,tail)
  }

  def eachListContains(lists: List[List[Int]], value:Int) : Boolean = lists match  {
    case Nil => true
    case x :: tail if x contains value => eachListContains(tail,value)
    case x :: tail => false
  }

  case class Disc(nr:Int, positions:Int, position:Int)
}
