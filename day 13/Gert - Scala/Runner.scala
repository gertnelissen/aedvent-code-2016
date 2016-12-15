package main.scala.Day13

object Runner extends Runner {}

class Runner {
  type Coordinate = (Int, Int)
  type Field = Boolean
  type Grid = Array[Array[Field]]

  val validMoves = List((0, 1), (0, -1), (1, 0), (-1, 0))

  def run(size: Coordinate, target: Coordinate, seed: Int): Unit = {
    val grid = buildGrid(size, seed)
    println(sWithPath(grid, List((1, 1), target)))
    shortestPathTo(target, grid)
  }

  def shortestPathTo(target: Coordinate, grid: Grid): Unit = {
    val position = (1, 1)
    val visitedPositions = List(position)

    travel(position, target, grid)
  }

  def travel(from: Coordinate, to: Coordinate, grid: Grid): Unit = {
    var positions = List((from, List[Coordinate]()))
    var visitedPositions = List(s(from))

    var countPositions = List[String]()

    while(true) {
      println("%s positions:".format(positions.length))
      positions.foreach(p => println(s(p._1)))
      println("%s visited positions".format(visitedPositions.length))

      // get positions and remove them from current positions
      val toTake = 1//positions.length
      val positionsToConsider = positions.take(toTake)
      positions = positions.drop(toTake)

      // Puzzle 2: count all positions where history = 50 long
      countPositions = countPositions.union(positionsToConsider.filter(p => p._2.length <= 50).map(p => s(p._1)))

      // stop
      if(positionsToConsider.isEmpty) {
        println("Positions in 50 steps: %s".format(countPositions.distinct.length))
        return
      }

      println("Considering %s positions, %s remain".format(
        s(positionsToConsider.head._1)//positionsToConsider.length
        , positions.length))

      positions = positions.union(
        positionsToConsider.flatMap(positionWithHistory => {
          val (position, history) = positionWithHistory
          getPossibleNewPositions(position, grid, visitedPositions)
            .map(newPosition => (newPosition, history.union(List(position))))
        }).distinct
      )

      println("After adding new positions, there are now %s positions to consider:".format(positions.length))
      positions.foreach(p => println(s(p._1)))

      visitedPositions = visitedPositions.union(positions.map(p => s(p._1)))

      println()
    }
  }

  def getPossibleNewPositions(from: Coordinate, grid: Grid, visitedPositions: List[String]): List[Coordinate] = {
    getPossiblePositions(from, grid)
      .filter(position => !visitedPositions.contains(s(position)))
  }

  def getPossiblePositions(from: Coordinate, grid: Grid): List[Coordinate] = {
    val possibleMoves = validMoves
      .map(validMove => (from._1 + validMove._1, from._2 + validMove._2))
      .filter(move => move._1 >= 0 && move._2 >= 0)
      .filter(move => move._1 < grid.length && move._2 < grid(0).length)
    possibleMoves.filter(possibleMove => grid(possibleMove._1)(possibleMove._2))
  }

  def getGridSize(target: Coordinate): Coordinate = {
    (target._1 + 2, target._2 + 2)
  }

  def buildGrid(size: Coordinate, seed: Int): Grid = {
    (0 to size._2).map(y =>
      (0 to size._1).map(x => calculateField((x, y), seed)).toArray).toArray
  }

  def calculateField(coordinate: Coordinate, seed: Int): Field = {
    val baseValue = f(coordinate._1, coordinate._2) + seed
    val binary = baseValue.toBinaryString
    val numberOfOnes = binary.filter(bit => bit == '1')
    numberOfOnes.length % 2 == 0
  }

  def f(x: Int, y: Int): Int = {
    (x*x) + (3*x) + (2*x*y) + y + (y*y)
  }

  def s(grid: Grid): String = {
    grid.map(_.map(cell => if(cell) '.' else '#').mkString("")).mkString("\n")
  }

  def sWithPath(grid: Grid, path: List[Coordinate]): String = {
    grid.indices.map(x => {
      grid(x).indices.map(y => {
        if(path.contains((x,y))) 'O'
        else if(grid(x)(y)) '.'
        else '#'
      }).mkString("")
    }).mkString("\n")
  }

  def s(coordinate: Coordinate): String = {
    "(%s,%s)".format(coordinate._1, coordinate._2)
  }
}
