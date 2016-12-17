import java.security.MessageDigest

object Vault2 extends App{
  type Coords = (Int,Int)

  val passCode = "pvhmgsws"
  val initialState = State((0,0),"")
  var states = Vector(initialState)
  var longestPath = initialState

  Stream.continually(expand()).find( list => list.isEmpty)
  println(longestPath.oldPath.length)

  def expand() : Vector[State] = {
    states = (states ++ states(0).getNextStates).drop(1)
    longestPath = states.filter(s => s.c == (3,3)).lastOption.getOrElse(longestPath)
    states
  }

  case class State(c: Coords, oldPath: String){
      def getNextStates : Vector[State] = {
        val digest = MessageDigest.getInstance("MD5")
        val doors = digest.digest((Vault2.passCode+oldPath).getBytes).map("%02x".format(_)).mkString.take(4)
        if(c == (3,3)) return Vector()
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
