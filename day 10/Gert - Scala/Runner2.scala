package main.scala.Day10

object Runner2 extends Runner {
  override def run(input: String, values: List[Int]): Array[Int] = {
    Array(run(parseInput(input), stopCheck(values))
      .flatMap(_.values).product)
  }

  override def stopCheck(values: List[Int]): Graph => Array[Bucket] = {
    graph: Graph => {
      val outputs = graph.buckets
        .filter(_.bucketType == BucketType.output)
        .filter(output => values.contains(output.id))

      if(outputs.length == values.length)
        outputs
      else
        Array()
    }
  }
}