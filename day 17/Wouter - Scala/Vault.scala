import java.security.MessageDigest

object Vault extends App{
  val t0 = System.nanoTime()
  type Coords = (Int,Int)

  val passCode = "pvhmgsws"
  val initialState = State((0,0),"")
  var states = Vector(initialState)

  println(Stream.continually(expand()).find( list => list.exists( s => s.c == (3,3))).get.find(s => s.c == (3,3)).get)

  val t1 = System.nanoTime()
  println(t1 - t0 + " ns")
  def expand() : Vector[State] = {
    states = (states ++ states(0).getNextStates).drop(1)
    states
  }

  case class State(c: Coords, oldPath: String){
      def getNextStates : Vector[State] = {
        val digest = MessageDigest.getInstance("MD5")
        val doors = digest.digest((Vault.passCode+oldPath).getBytes).map("%02x".format(_)).mkString.take(4)
        (for(i <- 0 until doors.length) yield {
          i match {
            case 0 => if(isOpenDoor(doors.charAt(i)) && c._2 > 0) Some(State((c._1,c._2 - 1),oldPath + "U")) else None
            case 1 => if(isOpenDoor(doors.charAt(i)) && c._2 < 3) Some(State((c._1,c._2 + 1),oldPath + "D")) else None
            case 2 => if(isOpenDoor(doors.charAt(i)) && c._1 > 0) Some(State((c._1 - 1,c._2 ),oldPath + "L")) else None
            case 3 => if(isOpenDoor(doors.charAt(i)) && c._1 < 3) Some(State((c._1 + 1,c._2),oldPath + "R")) else None
          }
        }).flatten.toVector
      }

      def isOpenDoor(c: Char) : Boolean = if(c.isDigit || c == 'a') false else true
  }
}
