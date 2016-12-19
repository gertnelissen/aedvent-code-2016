package main.scala.Day17

object Runner2 extends Runner {
  override def run(input: String): String = {
    Iterator.iterate((List(((0, 0), input)), -1))
      {states => {
        // get new set of states
        val newStates = states._1
          .filter(state => !vaultReached(state))
          .flatMap(state => iterate(state))

        // get the new longest solution yet, if there is any
        val solution = newStates.find(vaultReached)
        val longestSolutionYet =
          if(solution.isDefined)
            solution.get._2.drop(input.length).length
        else
          states._2

        (newStates, longestSolutionYet)
      }}
      .find(states => states._1.isEmpty).get._2.toString
  }
}