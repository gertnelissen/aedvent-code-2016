package main.scala.Day15

object Runner {
  type Positions = Int
  type InitialPosition = Int
  type Delay = Int
  type Gear = (Positions, InitialPosition, Delay)
  type config = List[Gear]

  def run(config: List[Gear]): Int = {
    Stream.from(0).find(timeTick => fallThrough(timeTick, config)).get
  }

  def fallThrough(delay: Int, config: List[Gear]): Boolean = {
    config.map(gear => {
      val timeReached = delay + gear._3
      val positionWhenReached = (timeReached + gear._2) % gear._1
      positionWhenReached == 0
    }).forall(aligned => aligned)
  }
}
