package c34

class a1{}
object a1{}

class a2{}

object a3 {

  def main(args: Array[String]): Unit = {
    import java.util.{Date=>UtilDate}
    import java.sql.{Date=>SqlDate}
    println(new UtilDate())
    println(new SqlDate(System.currentTimeMillis()))
  }
}

class a4 {
  var name = "张三"
}
object a4 {
  def main(args: Array[String]): Unit = {
    var a = new a4
    println(a.name)
    a.name="李四"
    println(a.name)
  }
}
import c34.a.b.a5

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
package a{

  package b{
    class a5 {
      var a = 1
      protected var b = 2
      private var c = 3
      private[a] var d = 4
    }
    object a5 {
      def main(args: Array[String]): Unit = {
        var x = new a5
//        println(x.a)
//        println(x.b)
//        println(x.c)
//        println(x.d)
      }
    }
    object a6 {
      def main(args: Array[String]): Unit = {
        var x = new a5
                println(x.a)
//                println(x.b)
        //        println(x.c)
                println(x.d)
      }
    }
  }
  object a7 {
    def main(args: Array[String]): Unit = {
      var x = new a5
              println(x.a)
      //        println(x.b)
      //        println(x.c)
              println(x.d)
    }
  }
}

object a8 {
  def main(args: Array[String]): Unit = {
    var x = new a5()
            println(x.a)
    //        println(x.b)
    //        println(x.c)
//            println(x.d)
  }
}

object a9 extends App {
  var x = 10
  println(x)
}

class b1 {
  var name = "b1"
}

class b2(name:String = "b2") {
  def f()={
    println(name)
  }
}

class b3(var name:String) {
  def f()={
    println(name)
  }
}

object b10 {
  def main(args: Array[String]): Unit = {
    var b1 = new b2("b22")
//    println(b1.name)
    b1.f()
    var b2 = new b2
    b2.f()
    var b3 = new b3("b33")
    b3.f
    println(b3.name)
  }
}

class c1 {
  var name = "c1"
  def f1()={
    println(name)
  }
}

class c2 extends c1 {
  override def f1(): Unit = {
    super.f1()
  }
}

object c2 {
  def main(args: Array[String]): Unit = {
    var c = new c2
    c.f1()
  }
}

abstract class c3 {
  var name:String
  def f1
  def f3
}

class c4 extends c3 {

  var name: String = "zs"

  def f1(): Unit = {
    println(name)
  }

  override val f3 = 10
}

object c5 {
  def main(args: Array[String]): Unit = {
    var c = new c4
    println(c.name)
    c.f1()
  }
}

object d1 {
  def main(args: Array[String]): Unit = {
    var m = Map("a"->1,"b"->2,"c"->3)
    var s = Set("a","b","c")
    var l = List(1,2,3,4,5,"a", 1.0)
    var a = Array(1,2,3,4,5,"a", 1.0)
    println(m,s,l,a)
  }
}

object d2 {
  def main(args: Array[String]): Unit = {
    var a1 = new Array[Int](10)
    println(a1.mkString(","))
    var a2 = Array.fill(10)(Random.nextDouble().toString.substring(0,4).toDouble)
    println(a2.mkString(","))
    println(a2.length, a2.size)

    var a3 = ArrayBuffer(5,4,3,2,1)
    a3.append(6)
    println(a3)
    a3.append(7,8,9,10)
    println(a3)
    a3 += (11,12,13)
    println(a3)

    a3.remove(1)
    println(a3)
    a3 -= (1,2)
    println(a3)


  }
}

object d3 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1)
    var l2 = Nil
    var l3 = List()
    println(l2,l3)

    var l4 = List(6,7,8)
    println(l1 :: l4)
    println(1::2::3::l4)
    println(l1 ::: l4)
    println(List.concat(l1,l4))

    var l5 = List(6,2,8,3,5)
    println(l5.sorted)
    println(l5.sorted.reverse)

  }
}