package test

case class Path(currentState:State, prevStates: Vector[State]) {
  def produceNextPaths() = currentState.produceNextValidStates().filterNot(prevStates.contains).map( s => Path(s, prevStates :+ s))
  def isDone = currentState.floors(3).size == 10
}
