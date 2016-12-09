package main.scala.Day09

object Runner2 extends Runner {
  override def replicate(replicator: (Int, Int), remainder: String): Long = {
    val toReplicate = remainder.take(replicator._1)
    val rest = remainder.substring(replicator._1)

    (run(toReplicate) * replicator._2) + run(rest)
  }
}
