object 测试var_val {
  def main(args: Array[String]): Unit = {
    var a=1
    var b="b"
    var c=1.0
    a+=1
    b+="0"

    val a2 =1
    val b2 = "b"
    val c2 = 1.0

    var p1 = new People()
    println(p1.name)
    println(p1.age)
    p1.name += 1
//    p1.age+=1

    val p2 = new People()
    p2.name +=1
    println(p2.name)
  }
}

class People {
  var name:String = "张三"
  val age = 20
}

object 测试数据类型 {
  def main(args: Array[String]): Unit = {
    var a1 = 1
    var a2:Byte = 1
    var a3:Short =1
    var a4:Long = 1
    var a5 = 1L

    var b1 = 1.0
    var b2 = 1.0f

    var c1 = true
    var c2 = false

    var d1 = 'd'
    var d2 = '\u0061'
    var d3 = '\\'

    var d4 = "dddd"

    var d5 =
"""
hello
world
"""

    var e1 = new People()
    e1 = null
    var e2 = 1

    def f1:Double = {
      println("hello")
      3
    }

    println(f1 +1)

    var g1 = 1.234567890123456789D
    println(g1)
    var g2:Float = g1.toFloat
    println(g2)
    var g3:Double = g2.toDouble
    println(g3)

  }

}

object ttt {
  def main(args: Array[String]): Unit = {
    val a = Array(1,2,3)
//    println(a*2)
    println("a"*2)
  }
}












