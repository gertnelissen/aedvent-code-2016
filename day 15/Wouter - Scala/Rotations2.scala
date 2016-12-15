import scala.collection.BitSet

object Rotations2 extends App{
  val inputRE = ".*Disc #(\\d+) has (\\d+).*position (\\d+).*".r
  val searchScope = 4000000

  val discs : Vector[Disc] = Util.readAllLines.map[Disc,Array[Disc]]{
    case inputRE(nr,positions,position) => Disc(nr.toInt,positions.toInt,position.toInt)
  }.toVector :+ Disc(7,11,0)

  val bs =
  println(discs)
  val baseTimesTable : Vector[BitSet] = convertToBaseTimeTable(discs.map(getTimesAtWhichDiscIsAtPosition0))
  val z = BitSet()
  def getTimesAtWhichDiscIsAtPosition0(disc: Disc) : BitSet = l2bs(for(i <- disc.positions - disc.position to searchScope by disc.positions ) yield i)
  def convertToBaseTimeTable(timesTable: Vector[BitSet]) : Vector[BitSet] = (for(index <- timesTable.indices) yield subtractPosition(timesTable(index),index+1)).toVector
  def subtractPosition(times:BitSet,positionInList:Int) : BitSet = times.map(t => if(t - positionInList >= 0) t-positionInList else 0)
  def largestDisc(discs:Vector[Disc]) : Disc = discs.sortBy(_.positions).last

  println(findSolution(baseTimesTable,baseTimesTable(discs.indexOf(largestDisc(discs)))))

  def findSolution(baseTimeTable: Vector[BitSet],largestDiscBaseTimes: BitSet) : Int = largestDiscBaseTimes match {
    case x if x.isEmpty => -1
    case x if eachListContains(baseTimeTable,x.head) => x.head
    case x => findSolution(baseTimeTable,x.tail)
  }

  def eachListContains(lists: Vector[BitSet], value:Int) : Boolean = lists match  {
    case x if x.isEmpty => true
    case x if x.head contains value => eachListContains(x.tail,value)
    case _ => false
  }

  def l2bs(ints: IndexedSeq[Int]) : BitSet = {
    val bitset= BitSet(ints:_*)
    bitset
  }

  case class Disc(nr:Int, positions:Int, position:Int)
}
