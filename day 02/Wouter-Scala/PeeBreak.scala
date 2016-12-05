object PeeBreak extends App{
  var code = ""
  var keypad = new Keypad
    Util.readStdIn.foreach(
      input => addCode(input)
    )
  def addCode(instructions : String) = {
    code += keypad nextNumber instructions
    println(code)
  }
}

class Keypad {
  var numbers = List[KeypadNumber](
    KeypadNumber(1,1,2,4,1),
    KeypadNumber(2,2,3,5,1),
    KeypadNumber(3,3,3,6,2),

    KeypadNumber(4,1,5,7,4),
    KeypadNumber(5,2,6,8,4),
    KeypadNumber(6,3,6,9,5),

    KeypadNumber(7,4,8,7,7),
    KeypadNumber(8,5,9,8,7),
    KeypadNumber(9,6,9,9,8)
  )
  var currentKeypadNumber = keypadNumber(5)
  def nextNumber(instructions: String) = {
    instructions.foreach( instruction => {
        currentKeypadNumber = currentKeypadNumber call instruction.toString
    })
    currentKeypadNumber.nr
  }

  def keypadNumber(nr: Int) = numbers.find(keypadNr => keypadNr.nr == nr).get

  case class KeypadNumber(val nr:Int, up:Int, right: Int, down: Int, left: Int){
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