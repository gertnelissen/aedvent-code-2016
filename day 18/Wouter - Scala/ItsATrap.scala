object ItsATrap extends App{
  var safe = 0
  println(solve(lineToRow("^.^^^.^..^....^^....^^^^.^^.^...^^.^.^^.^^.^^..^.^...^.^..^.^^.^..^.....^^^.^.^^^..^^...^^^...^...^."),400000))

  def solve(row: Vector[Boolean], rows: Int) : Int = {
    countSafe(row)
    rows match {
      case 1 => safe
      case _ =>
        val next = (Vector(false) ++ row ++ Vector(false)).sliding(3).map({
          case Vector(true,true,false) => true
          case Vector(false,true,true) => true
          case Vector(true,false,false) => true
          case Vector(false,false,true) => true
          case _ => false
        }).toVector
        solve(next,rows-1)
    }
  }

  def countSafe(vector: Vector[Boolean]) = safe += vector.count(_ == false)
  def lineToRow(in: String) : Vector[Boolean] = {
    in.map({
      case '.' => false
      case '^' => true
    }).toVector
  }
}
