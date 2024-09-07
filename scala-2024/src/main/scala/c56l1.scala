object c56l1 {
  def main(args: Array[String]): Unit = {
//    var a = null
//    a="123"
//    print(a)
  }
}

object c52l12{
  def main(args: Array[String]): Unit = {
    def b():Unit = {
      println("我是b")
    }
    a()
    b()
    var c_var = 1
    def c():Int = {
      c_var+=1
      c_var
    }
    println(c())
    println(c())
    println(c())
  }
  def a(): Unit = {
    println("我是a")
  }
}
