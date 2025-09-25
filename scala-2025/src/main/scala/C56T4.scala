package p564

import scala.collection.mutable.ArrayBuffer

package p1 {
  package p2{
    class MyClass561 {
      var name = "zhangsan"
      private var age = 18
      protected var address = "kaifeng"
      private[p1] var gender = "男"
      private[p2] var phone = 1234567
    }
    object MyClass563 {
      def main(args: Array[String]): Unit = {
        var stu = new MyClass561()
        println(stu.name)
//                    println(stu.age)
//                    println(stu.address)
        println(stu.gender)
        println(stu.phone)
      }
    }
  }
  package p3 {
    class MyClass564 {
      var name = "wangwu"
      private var age = 20
      protected var address = "zhengzhou"
      private[p1] var gender = "男"
//      private[p2] var phone = 7654321
    }
    object MyClass564 {
      def main(args: Array[String]): Unit = {
        var stu = new MyClass564()
        println(stu.name)
        println(stu.age)
        println(stu.address)
        println(stu.gender)
//        println(stu.phone)
      }
    }
    object MyClass565 {
      def main(args: Array[String]): Unit = {
        import p1.p2.MyClass561
        var stu = new MyClass561()
        println(stu.name)
//        println(stu.age)
//        println(stu.address)
        println(stu.gender)
//        println(stu.phone)
      }
    }
  }

  object MyClass562 {
    def main(args: Array[String]): Unit = {
      import p1.p2.MyClass561
      var stu = new MyClass561()
            println(stu.name)
//            println(stu.age)
//            println(stu.address)
            println(stu.gender)
//      println(stu.phone)
    }
  }
  object MyClass561 {
    def main(args: Array[String]): Unit = {
      import p1.p2.MyClass561
      var stu = new MyClass561()
      stu.name = "lisi"
      println(stu.name)
//      stu.age = 19
//      println(stu.age)
//      stu.address = "洛阳"
//      println(stu.address)
      stu.gender = "女"
      println(stu.gender)
    }
  }
}

package p2 {
  class MyClass566 {
    var name = "zhangsan"
  }
  object MyClass566 {
    var name = "lisi"

    def main(args: Array[String]): Unit = {
      println(name)
      println(new MyClass566().name)
    }
  }
}

object MyClass567 extends App{
  println("zhangsan")
}

class MyClass569() {
  var name:String = _
//  def this(n:String){
//    name = n
//  }
}

class MyClass568(name:String,age:Int) {

  def this() {
    this("zhangsan",18)
  }
  def this(n:String) {
    this(n,18)
  }


}

object MyClass568 {
  def main(args: Array[String]): Unit = {
    var obj = new MyClass568("zhangsan")
    new MyClass568("lisi",19)
    new MyClass568()
  }
}


class MyClass5610 {
  var name = "zhangsan"
  private var age = 18
  protected var address = "kaifeng"
}
object MyClass5610 {
  def main(args: Array[String]): Unit = {
    var stu = new MyClass5610()
    println(stu.name)
    println(stu.age)
    println(stu.address)
  }
}
class MyClass5611 extends MyClass5610 {
}
object MyClass5611 {
  def main(args: Array[String]): Unit = {
    var stu = new MyClass5611()
    println(stu.name)
//    println(stu.age)
    println(stu.address)
  }
}


object MyList561{
  def main(args: Array[String]): Unit = {
    var a1 = new Array[Int](10)
    var a2 = Array(1,2,3,4,5)

    println(a1.toList)
    println(a2.toList)

    println(a2(3))

    for(a <- a2){
      print(a,"")
    }
    a2.foreach(x=>print(x))

    a2(3) = 10
    a2.foreach(x=>print(x))
    println()
    println(a2.mkString(","))
    // a1-a2-a3-a4,b1-b2-b3-b4,
    // k1:v1,k2:v2,k3:v3,...
    var a3 = ArrayBuffer(1,2,3,4,5)
    a3.append(6,7,8)
    println(a3.toList)
    a3 += (9,10,11)
    println(a3.toList)

    a3.remove(1,2)
    println(a3.toList)
    a3 -= (5,6)
    println(a3.toList)

    a3.toBuffer




  }
}

