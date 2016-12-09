package main.scala.Day09

object Runner2 extends Runner {
  override def replicate(chars: Int, times: Int, remainder: String): Long = {
    run(remainder.take(chars)) * times + run(remainder.substring(chars))
  }
}