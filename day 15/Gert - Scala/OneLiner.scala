package main.scala.Day15

object Runner {
  type Gear = (Int, Int, Int)

  def run(config: List[Gear]): Int = {
    Stream.from(0).find(timeTick =>
      config.forall(gear => (timeTick + gear._3 + gear._2) % gear._1 == 0)).get
  }
}
