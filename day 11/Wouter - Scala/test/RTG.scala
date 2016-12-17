package test

import scala.collection.immutable.HashSet


object RTG extends App{
  val thuliumGenerator = 1
  val thuliumMicrochip = 11
  val plutoniumGenerator = 2
  val plutoniumMicrochip = 12
  val strontiumGenerator = 3
  val strontiumMicrochip = 13
  val promethiumGenerator = 4
  val promethiumMicrochip = 14
  val rutheniumGenerator = 5
  val rutheniumMicrochip = 15

  val initialState = State(0,Vector(
    HashSet(thuliumGenerator,thuliumMicrochip,plutoniumGenerator,strontiumGenerator),
    HashSet(plutoniumMicrochip,strontiumMicrochip),
    HashSet(promethiumGenerator,promethiumMicrochip,rutheniumGenerator,rutheniumMicrochip),
    HashSet()))

  val initialPath = Path(initialState,Vector())
  var paths = Vector[Path](initialPath)

  val result = Stream.continually(expand()).find( _.exists( _.isDone )).get.find(_.isDone).get
  println(result)
  println(result.prevStates.length-1)

  def expand() : Vector[Path] = {
    paths = (paths ++ paths(0).produceNextPaths()).drop(1)
    paths
  }

}
