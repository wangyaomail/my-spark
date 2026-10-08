package c12.l3

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

package l31 {
  class a1 {
    var a = 1
    var b = "b"
    var c = 'c'
    var d = true
  }

  object a1 {
    def main(args: Array[String]): Unit = {
      var a = new a1
      println(a.a, a.b, a.c, a.d)
    }
  }
}
class a2 {

}
object a2 {
  def main(args: Array[String]): Unit = {
    import l31.a1
    var a = new a1
//    import java.util._
//    import java.sql._
//    var x = new Date()
//    println(x)

    import java.util.{Date => UtilDate}
    import java.sql.{Date => SqlDate}
    var x = new UtilDate()
    println(x)
  }
}


package l32{
  package l33{
    class b1 {
      var a = 1
      protected var b = "b"
      private var c = 'c'
      private[l32] var d = true
      def f()={
        println(a,b,c,d)
      }
    }
    object b1 {
      def main(args: Array[String]): Unit = {
        var b = new b1
//        println(b.a)
//        println(b.b)
//        println(b.c)
//        println(b.d)
      }
    }
    object b2 {
      def main(args: Array[String]): Unit = {
        var b = new b1
        b.f()
//                println(b.a)
//                println(b.b)
        //        println(b.c)
//                println(b.d)
      }
    }
  }
  object b3 {
    def main(args: Array[String]): Unit = {
      import l33.b1
      var b = new b1
      b.f()
                      println(b.a)
      //                println(b.b)
      //        println(b.c)
                      println(b.d)
    }
  }
}

object b4 {
  def main(args: Array[String]): Unit = {
    import l32.l33.b1
    var b = new b1
    b.f()
                    println(b.a)
    //                println(b.b)
    //        println(b.c)
    //                println(b.d)
  }
}

object a4 {

}

object a5 extends App {
  println("a5")
  println("a5")
  println("a5")
}

object a6 extends App {
  var x = 10
  println(x)
}

class a7(name:String) {
  def f()={
    println(name)
  }

}

class a8{
  var name = "a8"
}

class a9(var name:String) {

}

object a10 {
  def main(args: Array[String]): Unit = {
    var a7 = new a7("a7")
    var a8 = new a8
    var a9 = new a9("a9")
//    println(a7.name)
    a7.f()
    println(a8.name)
    println(a9.name)
  }
}

class c1 {
  var name = "c1"
//  def f1;
}

class c2 extends c1 {
  def f2()={
    println(name)
  }
}

abstract class c3 {
  var name = "c3"
  val age = 20
  def f3()
  def f4()={
    println(name)
  }
}

class c4 extends c3 {
  override val age: Int = 22
  def f3(): Unit = {
    println("c4")
  }

  override def f4(): Unit = {
    println("new f4")
  }
}

object c10 {
  def main(args: Array[String]): Unit = {
    var c2 = new c2
    c2.f2()
    var c4=  new c4()
    c4.f3
    c4.f4
    var c5:c3 = new c4
    c5.f4
  }
}


object d1 {
  def main(args: Array[String]): Unit = {
    var m1 = Map("a"->1, "b"->2)
    var s1 = Set("a","b")
    var a1 = Array(1,2,3)
    var l1 = List(1,2,3)
    println(m1,s1,a1,l1)
  }
}

object d2 {
  def main(args: Array[String]): Unit = {
    var a1 = new Array[Int](10)
    println(a1.mkString(","))
    var a2 = Array(1,2,3,4,5)
    println(a2(1))
    println(a2)
    a2(3) = 10
    println(a2.mkString(","))
    println(a2.length,a2.size)
    var a3 = ArrayBuffer(5,4,3,2,1)
    a3.append(6,7)
    println(a3.mkString(","))
    a3 += (8,9,10)
    println(a3.mkString(","))
    a3.remove(2)
    println(a3.mkString(","))
    a3 -= (5,4)
    println(a3.mkString(","))
  }
}

object d3 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1)
    println(l1.map(_+1))
    var l2 = List(7,8,9)
    println(l1::l2)
    println(l1:::l2)
    println(List.concat(l1,l2))
    println(List.fill(10)(Random.nextDouble()))
    var l3 = List(6,2,8,4,3,9,10)
    println(l3)
    println(l3.sorted)
    println(l3.sorted.reverse)

  }
}