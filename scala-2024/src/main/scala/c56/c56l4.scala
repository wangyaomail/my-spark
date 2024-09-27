package c56
package l4

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


package p6 {
  package p7 {
    package p8 {
      object MyObject2 {
        def main(args: Array[String]): Unit = {
          var c1 = new  MyClass1
          println(c1.x)
          //          println(c1.y)
          //          println(c1.z)
          println(c1.k)
        }
      }
    }

    class MyClass1 {
      var x = "x"
      protected var y = "y"
      private var z = "z"
      private[p7] var k = "k"
      println("class 1")
    }

    object MyClass1 {
      println("object 1")

      def apply(): MyClass1 = new  MyClass1

      def main(args: Array[String]): Unit = {
        var c1 = new  MyClass1
        println(c1.x)
        println(c1.y)
        println(c1.z)
        println(c1.k)
      }
    }

    object MyObject3 {
      def main(args: Array[String]): Unit = {
        var myc = new MyClass1
        println(myc)
      }
    }

    object MyObject1 {
      println("hello2")

      def main(args: Array[String]): Unit = {
        var c1 = new MyClass1
        println(c1.x)
        //        println(c1.y)
        //        println(c1.z)
        println(c1.k)
      }
    }
  }

}


class MyClass4 {
  println("a")

  def this(i: Int) {
    this
    println("b")
  }

  def this(s: String) = {
    this
    println("c")
  }
}

object MyClass4 {
  def main(args: Array[String]): Unit = {
    var c4 = new MyClass4("x")
  }
}

class MyClass5 {
  val name = "zhangsan"
}

class MyClass6 extends MyClass5 {
  override val name = "lisi"
}

object MyObject6 {
  def main(args: Array[String]): Unit = {
    println(new MyClass6().name)
  }
}

object MyClass7 {
  def main(args: Array[String]): Unit = {
    var arr: Array[Int] = new Array[Int](10)
    arr = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    //    println(arr)
    //    arr.foreach(println)
    //    for(x<- arr){
    //      println(x)
    //    }
    println(arr.mkString(","))
    println(arr(1))
    arr(1) = 100
    println(arr(1))
    println(arr.length)
    println(arr.size)

    var x = new ArrayBuffer[Int]()
    x +=(1, 2, 3)
    print(x)

  }


}

object MyObject8 {
  def main(args: Array[String]): Unit = {
    var l1 = Nil
    println(l1)
    var l2 = List(1, 2, 3, 4, 5)
    println(l2)
    var l3 = List("a", "b", "c")
    var l4 = l2 :: l3
    println(l4)
    var l5 = l2 ::: l3
    println(l5)
    var l6 = List.concat(l2, l3)
    println(l6)
    var l7 = List.fill(10)("x")
    println(l7)
    var l8 = l2.reverse
    println(l8)
    var l9 = l8.sorted
    println(l9)
  }
}

object MyObject9 {
  def main(args: Array[String]): Unit = {
    var s1 = Set(1, 2, 3, 4, 3, 4)
    println(s1)
    var s2 = s1 +(1, 3, 5, 7, 9)
    println(s2)
    var s3 = s1 +(1, 3, 5, 7, 9)
    println(s3)
    var s4 = mutable.Set(1, 2, 3)
    s4.add(2)
    s4.add(5)
    println(s4)
  }
}

object MyObject10 {
  def main(args: Array[String]): Unit = {
    var m1 = Map("a" -> 1, "b" -> 2, "c" -> 3)
    println(m1)
    println(m1("a"))
    println(m1 + ("d" -> 4))
    println(m1 - "a")
    println(m1.updated("a", 5))
    println(m1.get("a"))
    println(m1.get("e"))
    println(m1.get("e").getOrElse(0))
  }
}

object MyObject11 {
  def main(args: Array[String]): Unit = {
    var t1 = (1, 1.0f, "abc")
    println(t1)
    println(t1._1, t1._2)
    var m1 = Map(("a", 1), ("b", 2), ("c", 3))
    println(m1)
  }
}


object MyObject12 {
  def main(args: Array[String]): Unit = {
    var a = "1"
    a match {
      case "1" => println("a")
      case "2" => println("b")
      case _ => println("i dont know")
    }
    var b = "3"
    b match {
      case "1" => println("a")
      case "2" => println("b")
      case _ if b.toInt > 2 => println("over 2")
      case _ => println("i dont know")
    }
  }
}






