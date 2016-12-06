object DataFolder extends App {
  def openFile(filename: String): io.BufferedSource = {
    io.Source.fromURL(getClass.getResource(filename))
  }
}
