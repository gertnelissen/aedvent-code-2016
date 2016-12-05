object PeeBreak2 extends App{
  var code = ""
  var keypad = new Keypad2
    Util.readStdIn.foreach(
      input => addCode(input)
    )
  def addCode(instructions : String) = {
    code += keypad nextNumber instructions
    println(code)
  }
}

class Keypad2 {
  var numbers = List[KeypadNumber](
    KeypadNumber("1","1","1","3","1"),
    KeypadNumber("2","2","3","6","2"),
    KeypadNumber("3","1","4","7","2"),
    KeypadNumber("4","4","4","8","3"),
    KeypadNumber("5","5","6","5","5"),
    KeypadNumber("6","2","7","A","5"),
    KeypadNumber("7","3","8","B","6"),
    KeypadNumber("8","4","9","C","7"),
    KeypadNumber("9","9","9","9","8"),
    KeypadNumber("A","6","B","A","A"),
    KeypadNumber("B","7","C","D","A"),
    KeypadNumber("C","8","C","C","B"),
    KeypadNumber("D","B","D","D","D")

  )
  var currentKeypadNumber = keypadNumber("5")
  def nextNumber(instructions: String) = {
    instructions.foreach( instruction => {
        currentKeypadNumber = currentKeypadNumber call instruction.toString
    })
    currentKeypadNumber.nr
  }

  def keypadNumber(nr: String) = numbers.find(keypadNr => keypadNr.nr == nr).get

  case class KeypadNumber(val nr:String, up:String, right: String, down: String, left: String){
    def U(): KeypadNumber = keypadNumber(up)
    def R(): KeypadNumber = keypadNumber(right)
    def L(): KeypadNumber = keypadNumber(left)
    def D(): KeypadNumber = keypadNumber(down)
  }

  case class Caller[T>:Null<:AnyRef](klass:T) {
    def call(methodName:String,args:AnyRef*):KeypadNumber = {
      def argtypes = args.map(_.getClass)
      def instruction = klass.getClass.getMethod(methodName, argtypes: _*)
      instruction.invoke(klass,args: _*).asInstanceOf[KeypadNumber]
    }
  }
  implicit def anyref2callable[T>:Null<:AnyRef](klass:T):Caller[T] = new Caller(klass)
}