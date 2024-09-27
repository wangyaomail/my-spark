package c34
package l4

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


package p1{
  import c34.l4.p1.p2.MyClass1
  object MyObject1{
    def main(args: Array[String]): Unit = {
      var c1 = new MyClass1
//      println(c1.x)
//              println(c1.y)
//              println(c1.z)
//      println(c1.k)
    }
  }
  package p2{
    package p3{
      object MyObject1{
        def main(args: Array[String]): Unit = {
          var c1 = new MyClass1
                println(c1.x)
//                        println(c1.y)
//                        println(c1.z)
                println(c1.k)
        }
      }
    }
    class MyClass1{
      var x=1
      protected var y=2
      private var z=3
      private[p2] var k=4
    }
    object MyClass1{
      def main(args: Array[String]): Unit = {
        var c1 = new MyClass1
        println(c1.x)
        println(c1.y)
        println(c1.z)
        println(c1.k)
      }
    }
    object MyObject1{
      def main(args: Array[String]): Unit = {
        var c1 = new MyClass1
        println(c1.x)
//        println(c1.y)
//        println(c1.z)
        println(c1.k)
      }
    }
    class MyClass2 extends MyClass1{

    }
    object MyClass2 {
      def main(args: Array[String]): Unit = {
        var c1 = new MyClass2
        println(c1.x)
                println(c1.y)
//                println(c1.z)
        println(c1.k)
      }
    }
  }
}

object MyObject3 {
  def main(args: Array[String]): Unit = {
    println("hello")
  }
}

object MyObject4 extends App{
  println("hello")
}

class MyClass4(s:String) {
  var a = "1"
  var b = s
  println("a",a, "b", b)

  def this(){
    this("b2")
  }
  def this(s:String, i:Int) {
    this(s)
    println("i",i)
  }
}

object MyClass4{
  def main(args: Array[String]): Unit = {
    new MyClass4
    new MyClass4("b3")
    new MyClass4("b3", 1)

  }
}

object MyObject5{
  def main(args: Array[String]): Unit = {
    var arr = new Array[Int](5)
    arr = Array(1,2,3,4,5)
    println(arr(2))
    arr(2) = 10
    println(arr(2))
    for(a <- arr){
      println(a)
    }
    arr.foreach(print)
    println(arr.mkString(","))
//    k1:v1,k2:v2,....
    println(arr.length)
    println(arr.size)

    println(arr)

    var ab = ArrayBuffer[Int]()
    ab.append(1)
    ab.append(2)
    println(ab)

    ab.toArray
    arr.toBuffer


  }
}

object MyObject6{
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1)
    var l2 = Nil
    println(l2)
    var l3 = List()
    println(l3)
    var l4 = List(6,7)
    println(l1 :: l4)
    println( 1 :: 2 :: 3::4::5::List(6))
    println(l1 ::: l4)

    println(List.concat(l1, l4))

    println(List.fill(5)("x"))

    var l5 = List(1,5,3,7,2)
    println(l5.reverse)
    println(l5.sorted)
    println(l5.sorted.reverse)


  }
}

object MyObject7{
  def main(args: Array[String]): Unit = {
    var s1 = Set(1,2,3)
    var s2 = s1 + (1,2)
    println(s2)
    var s3 = mutable.Set(1,2,3)
    s3.add(4)
    s3.add(5)
    s3.add(2)
    s3.add(3)
    println(s3)
  }
}

object MyObject{
  def main(args: Array[String]): Unit = {
    var m1 = Map("a"->1, "b"->2, "c"->3)
    println(m1)
    println(m1("b"))
    m1 = m1.updated("b",4)
    println(m1("b"))
    println(m1.mkString(","))
    println(m1.get("d"))
    println(m1.get("d").getOrElse(0))
  }
}