package main.scala.Day3

object Runner2 extends Runner {
  override def validateTriangles(triangles: Array[String]) : Int = {
    super.validateTriangles(pivot(triangles))
  }

  def pivot(triangles: Array[String]) : Array[String] = {
    triangles.grouped(3).flatMap(group => {
      val trianglePattern(a1, b1, c1) = group(0)
      val trianglePattern(a2, b2, c2) = group(1)
      val trianglePattern(a3, b3, c3) = group(2)

      Array(
        "%s %s %s".format(a1, a2, a3),
        "%s %s %s".format(b1, b2, b3),
        "%s %s %s".format(c1, c2, c3)
      )
    }).toArray
  }
}
