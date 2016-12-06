object Signals2 extends App{
  val maps = Array[Map[Char,Int]](Map(),Map(),Map(),Map(),Map(),Map(),Map(),Map())
  Util.readAllLines.foreach(improveMessage)

  maps.foreach(map => {
    print(map.toList.sortBy(_._2).head._1)
  })

  def improveMessage(ok:String): Unit ={
    var counter = 0
    ok.foreach(char => {
      maps(counter) = maps(counter) + (char -> (maps(counter).getOrElse(char, 0)+1))
      counter+= 1
    })
  }
}
