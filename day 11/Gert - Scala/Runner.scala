package main.scala.Day11

import scala.collection.immutable.HashSet

object Runner {
  type HashKey = Int

  case class Floor(level: Int, generators: Int, microChips: Int)

  case class Config(elevatorPosition: Int, floors: Array[Floor], stepsFromBeginning: Int)

  def run(config: Config): Unit = {
    val solution = searchSolution(List(config), HashSet[HashKey]())
    println("Final config %s found after %s steps!".format(toString(solution), solution.stepsFromBeginning))
  }

  def searchSolution(configPool: List[Config], visitedConfigs: HashSet[HashKey]): Config = {
    // get best guess configs: they are furthest from the original configuration
    val (furthestConfigs, remainingConfigs) = getFurthestConfigs(configPool)

    // mark these configs as visited
    val newVisitedConfigs = visitedConfigs ++ furthestConfigs.map(toHashKey)

    // calculate which other configurations can be reached
    val reachableConfigs = getReachableConfigs(furthestConfigs, newVisitedConfigs)

    // check if solution is found or keep searching
    val solution = reachableConfigs.find(c => isSolved(c))
    if(solution.isDefined)
      solution.get
    else
      searchSolution(merge(reachableConfigs, remainingConfigs), newVisitedConfigs)
  }

  def getFurthestConfigs(configPool: List[Config]): (List[Config], List[Config]) = {
    val furthestDistance = configPool.map(c => c.stepsFromBeginning).max
    configPool.partition(c => c.stepsFromBeginning == furthestDistance)
  }

  /**
    * Merges configs and keeps the "laziest" one for each state
    */
  def merge(a: List[Config], b: List[Config]): List[Config] = {
    (a ::: b)
      .groupBy(c => toHashKey(c))
      .map(c => c._2.sortBy(_.stepsFromBeginning).head).toList
  }

  /**
    * Config generation
    */
  def getReachableConfigs(configs: List[Config], seenConfigs: HashSet[HashKey]): List[Config] = {
    configs.flatMap(config => {
      getReachableConfigs(config)
        .filter(isPossible)
        .filter(isSafe)
        .filter(c => !seenConfigs.contains(toHashKey(c)))
    })
  }

  // list of predefined possible moves, based on elevator capacity
  val carriages = List((0, 1), (1, 0), (1, 1), (2, 0), (0, 2))

  def getReachableConfigs(config: Config): List[Config] = {
    getNewElevatorPositions(config).flatMap(newElevatorPosition => {
      carriages.map(carriage => carry(carriage, newElevatorPosition, config))
    })
  }

  def getNewElevatorPositions(config: Config): List[Int] = {
    List(config.elevatorPosition - 1, config.elevatorPosition + 1)
      .filter(newElevatorPosition => (1 to 4).contains(newElevatorPosition))
  }

  def carry(carriage: (Int, Int), newElevatorPosition: Int, config: Config): Config = {
    val newFloors = config.floors.map(floor => {
      if(floor.level == config.elevatorPosition)
        new Floor(floor.level, floor.generators - carriage._1, floor.microChips - carriage._2)
      else if(floor.level == newElevatorPosition)
        new Floor(floor.level, floor.generators + carriage._1, floor.microChips + carriage._2)
      else
        floor
    })

    new Config(newElevatorPosition, newFloors, config.stepsFromBeginning + 1)
  }

  /**
    * Filter sanity of configs
    */
  def isPossible(config: Config): Boolean = config.floors.forall(isPossible)

  def isPossible(floor: Floor): Boolean = floor.generators >= 0 && floor.microChips >= 0

  def isSafe(config: Config): Boolean = config.floors.forall(isSafe)

  def isSafe(floor: Floor): Boolean = floor.generators == 0 || floor.microChips <= floor.generators

  /**
    * Solution checking
    */
  def isSolved(config: Config): Boolean = config.floors.filter(_.level != 4).forall(floor => isEmpty(floor))

  def isEmpty(floor: Floor): Boolean = floor.generators == 0 && floor.microChips == 0

  /**
    * Conversions
    */
  def toHashKey(config: Config): HashKey = {
    // faster hash key than state string representation
    // WARNING don't use if there are more than 10 generators or microchips
    "%s%s".format(
      config.elevatorPosition,
      config.floors.sortBy(_.level)
        .map(f => f.level * 100 + f.generators * 10 + f.microChips).mkString("")
    ).hashCode
  }

  def toString(config: Config): String = "%s|%s".format(toStateString(config), config.stepsFromBeginning)

  def toStateString(config: Config): String = "%s|%s".format(config.elevatorPosition, toString(config.floors))

  def toString(floors: Array[Floor]): String = floors.sortBy(_.level).map(toString).mkString(":")

  def toString(floor: Floor): String = "%s,%s".format(floor.generators, floor.microChips)
}