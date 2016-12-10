package main.scala.Day10

case class Handover(bot: Int, destinationType: BucketType.Value, toId: Int, getValue: (Option[Bucket] => Option[Int])) {}
