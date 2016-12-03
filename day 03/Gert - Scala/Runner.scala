package main.scala.Day3

object Runner extends Runner {}

class Runner {
  val trianglePattern = "\\s*([0-9]+)\\s+([0-9]+)\\s+([0-9]+)".r

  def validateTriangles(triangles: String) : Int = {
    validateTriangles(triangles.split("\r\n"))
  }

  def validateTriangles(triangles: Array[String]) : Int = {
    triangles.map(toTriangle)
      .map(t => isPossibleTriangle(t._1, t._2, t._3))
      .count(_ == true)
  }

  def toTriangle(triangle: String): (Int, Int, Int) = {
    val trianglePattern(a, b, c) = triangle
    (a.toInt, b.toInt, c.toInt)
  }

  def isPossibleTriangle(a: Int, b: Int, c: Int) : Boolean = {
    a + b > c &&
      b + c > a &&
      a + c > b
  }
}



