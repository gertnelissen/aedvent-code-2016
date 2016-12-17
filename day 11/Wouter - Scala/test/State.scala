package test

case class State(elevator: Int, floors: Vector[Set[Int]]) {

  def produceNextValidStates() = {
    produceNextStates filter isValid
  }

  def produceNextStates() : Vector[State] = {
    val combinations = (floors(elevator).toVector.combinations(2) ++ floors(elevator).toVector.combinations(1)).toList
    (elevator +1, elevator -1) match {
      case (4,2) => combinations.map( itemsToMove =>  State(2,moveItems(itemsToMove,3,2))).toVector
      case (1,-1) => combinations.map( itemsToMove => State(1,moveItems(itemsToMove,0,1))).toVector
      case (x,y) =>
        (combinations.map( itemsToMove => State(x,moveItems(itemsToMove,elevator,x))) ++
          combinations.map( itemsToMove => State(y,moveItems(itemsToMove,elevator,y)))).toVector
    }
  }

  def moveItems(items: Vector[Int], from: Int, to: Int) : Vector[Set[Int]] = {
    (for(i <- 0 to 3) yield i match {
      case `from` => floors(from) -- items
      case `to` => floors(to) ++ items
      case x => floors(x)
    }).toVector
  }

  def isValid(state: State) = state.floors forall isValidFloor
  def isValidFloor(floor: Set[Int]) = floor match {
    case cleanFloor if !floor.exists(item => item < 10) => true
    case messyFloor => floorIsStable(messyFloor)
  }
  def floorIsStable(floor: Set[Int]) = {
    floor.forall( item => if(item > 10) floor(item-10) else true)
  }
}
