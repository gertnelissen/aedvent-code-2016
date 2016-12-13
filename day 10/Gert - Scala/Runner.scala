package main.scala.Day10

object Runner extends Runner {}

trait Runner {
  val initialValuePattern = """value (\d+) goes to (\w+) (\d+)""".r
  val givesPattern = """bot (\d+) gives low to (\w+) (\d+) and high to (\w+) (\d+)""".r

  def run(input: String, values: List[Int]): Array[Int] = {
    run(parseInput(input), stopCheck(values)).map(_.id)
  }

  def run(graph: Graph, stopCheck: Graph => Array[Bucket]): Array[Bucket] = {
    stopCheck(graph) match {
      case Array() => run(doHandover(graph), stopCheck)
      case bucketsOfInterest => bucketsOfInterest
    }
  }

  def stopCheck(values: List[Int]): Graph => Array[Bucket] = {
    graph: Graph => graph.buckets.find(_.values.containsSlice(values)).toArray
  }

  def doHandover(graph: Graph): Graph = {
    val (handsFullBots, otherBuckets) = graph.buckets.partition(_.values.length == 2)
    val newBuckets = mergeBuckets(handoverValues(handsFullBots, graph.handovers).union(otherBuckets))
    Graph(newBuckets, graph.handovers)
  }

  def handoverValues(bots: Array[Bucket], handovers: Array[Handover]): (Array[Bucket]) = {
    handovers
      .map(handover => (handover, bots.find(_.id == handover.bot)))
      .filter(_._2.isDefined)
      .map(handoverWithBucket => {
        Bucket(
          handoverWithBucket._1.toId,
          Array(handoverWithBucket._1.getValue(handoverWithBucket._2).get),
          handoverWithBucket._1.destinationType)
      })
  }

  def mergeBuckets(buckets: Array[Bucket]): Array[Bucket] = {
    buckets.groupBy(bucket => (bucket.id, bucket.bucketType))
      .map(bucketGroup => Bucket(bucketGroup._1._1, bucketGroup._2.flatMap(_.values), bucketGroup._1._2))
      .toArray
  }

  def parseInput(input: String): Graph = {
    val (buckets, handovers) = input.split("\r\n").map(parse).unzip
    Graph(buckets.flatten, handovers.flatten)
  }

  def parse(input: String): (List[Bucket], List[Handover]) = {
    input match {
      case initialValuePattern(value, to, toId) =>
        (List(
          Bucket(toId.toInt, Array(value.toInt), BucketType.withName(to))
        ), List())

      case givesPattern(bot, lowTo, lowToId, highTo, highToId) =>
        (List(), List(
          Handover(bot.toInt, BucketType.withName(lowTo), lowToId.toInt, getValue(_.min)),
          Handover(bot.toInt, BucketType.withName(highTo), highToId.toInt, getValue(_.max))
        ))
    }
  }

  def getValue(f: Array[Int] => Int): Option[Bucket] => Option[Int] = {
    case Some(bucket) => Some(f(bucket.values))
    case None => None
  }
}