import scala.collection.immutable.HashSet

object Rotations2 extends App{
  val inputRE = ".*Disc #(\\d+) has (\\d+).*position (\\d+).*".r
  val searchScope = 4000000

  val discs : Vector[Disc] = Util.readAllLines.map[Disc,Array[Disc]]{
    case inputRE(nr,positions,position) => Disc(nr.toInt,positions.toInt,position.toInt)
  }.toVector :+ Disc(7,11,0)

  val baseTimesTable : Vector[HashSet[Int]] = convertToBaseTimeTable(discs.map(getTimesAtWhichDiscIsAtPosition0))
  def getTimesAtWhichDiscIsAtPosition0(disc: Disc) : HashSet[Int] = l2hs(for(i <- disc.positions - disc.position to searchScope by disc.positions ) yield i)
  def convertToBaseTimeTable(timesTable: Vector[HashSet[Int]]) : Vector[HashSet[Int]] = (for(index <- timesTable.indices) yield subtractPosition(timesTable(index),index+1)).toVector
  def subtractPosition(times:HashSet[Int],positionInList:Int) : HashSet[Int] = times.map(t => if(t - positionInList >= 0) t-positionInList else 0)
  def largestDisc(discs:Vector[Disc]) : Disc = discs.sortBy(_.positions).last

  println(findSolution(baseTimesTable,baseTimesTable(discs.indexOf(largestDisc(discs)))))

  def findSolution(baseTimeTable: Vector[HashSet[Int]],largestDiscBaseTimes: HashSet[Int]) : Int = largestDiscBaseTimes match {
    case x if x.isEmpty => -1
    case x if eachListContains(baseTimeTable,x.head) => x.head
    case x => findSolution(baseTimeTable,x.tail)
  }

  def eachListContains(lists: Vector[HashSet[Int]], value:Int) : Boolean = lists match  {
    case x if x.isEmpty => true
    case x if x.head contains value => eachListContains(x.tail,value)
    case _ => false
  }

  def l2hs(ints: IndexedSeq[Int]) : HashSet[Int] = {
    ints.foldLeft(HashSet[Int]())((set,int) => set + int)
  }

  case class Disc(nr:Int, positions:Int, position:Int)
}
