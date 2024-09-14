package c56


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
object c56l2 {
  def f5 = {
    print("hello")
  }
  def main(args: Array[String]): Unit = {
    val a = new c56l21
    println(a.name)
    a.name = "wangwu"
    println(a.name)
//    a= new c56l21
//    println(a.name)
    var x = 1.0
    x=2.0f
    var y=
      """
         123
         456
         789
      """
    println(y)
    var z = "123"
//    z=null
//    var o = 123
//    o=null
    var aa = 100

    def f1(a:Int):Unit = {
      print(z)
      z="456"
      print(z)
      a+10
    }
    f1(aa)
    println(aa)
    var bb = 123.456
    var cc = bb.toInt
    var dd = "123456"
    var ff = dd.toInt

    var gg = 100
    gg += 11
    println(gg)

    var hh = "abc"
    hh += "def"
    println(hh)
    if(hh=="abc"){
      if(gg>100)
        println(gg)
//        println(gg)
      else {
      }
    }

  }
}
class c56l21 {
  var name="zhangsan"
  def f4 = {
    print("hello")
  }
}

//class c56l22 extends c56l21 {
//  override val name="lisi"
//}

object c56l22{
  def main(args: Array[String]): Unit = {
    def f2:Int = {
      123
    }
    def f3:Int = f2
    println("abc")
    println(f3)
    var aa = 123
    def f4:Int = {aa += 1; aa}
    println(f4)
    println(f4)
    println(f4)
    println(1 to 10)
    for(i <- 1 to 10){
      println(i)
    }
  }
}


