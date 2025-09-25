package p784

package p1{
  class MyClassP781 {
    var name = "张三"
    private var age = 18
    protected var address = "洛阳"
    private[p1] var gender = "男"
  }
  class MyClassP785 extends MyClassP781{
  }
  object MyClassP785 {
    def main(args: Array[String]): Unit = {
      var stu = new MyClassP785()
                            println(stu.name)
//                            println(stu.age)
                            println(stu.address)
                            println(stu.gender)
    }
  }
  package p2{
    object MyClassP783 {
      def main(args: Array[String]): Unit = {
        var stu = new MyClassP781()
//                      println(stu.name)
//                      println(stu.age)
//                      println(stu.address)
//                      println(stu.gender)
      }
    }
  }
  object MyClassP782 {
    def main(args: Array[String]): Unit = {
      var stu = new MyClassP781()
//              println(stu.name)
//              println(stu.age)
//              println(stu.address)
              println(stu.gender)
    }
  }
  object MyClassP781 {
    def main(args: Array[String]): Unit = {
        var stu = new MyClassP781()
//        println(stu.name)
//        println(stu.age)
//        println(stu.address)
//        println(stu.gender)
    }
  }
}

object MyClassP784 {
  def main(args: Array[String]): Unit = {
//    var stu = new MyClassP781()
//                          println(stu.name)
//                          println(stu.age)
//                          println(stu.address)
//                          println(stu.gender)
  }
}

package p2 {
  class MyClassP786 {
    val name = "张三"
    private var age = 18
    protected var address = "洛阳"
  }
  class MyClassP787 extends MyClassP786 {
    override val name = "123"
//    def name() = {
//      println("李四")
//    }
  }
  object MyClassP787 {
    def main(args: Array[String]): Unit = {
      var stu = new MyClassP787()
      println(stu.name)
    }
  }
}

package p783{
  object MyClass788 {
    def main(args: Array[String]): Unit = {
      println("hi")
    }
  }

  object MyClass789 extends App{
    println("hello")
  }

  trait MyTrait7810 {
    val v1:Int
    def f1
    def f2(v1:Int)
    def f3(v1:Int)={
      println("f3",v1)
    }
  }

  class MyClass7810 extends MyTrait7810{
    override val v1: Int = 123
    override def f1(): Unit = {
      println("f1")
    }
    override def f2(v1: Int): Unit = {
      println("f2",v1)
    }
  }
  object MyClass7810 {
    def main(args: Array[String]): Unit = {
      var t1 = new MyClass7810()
      println(t1.v1)
      t1.f1()
      t1.f2(999)
      t1.f3(888)
    }
  }

}

package p784{

  class MyClass78() {
    var name:String=_
    var age:Int=_
  }

  class MyClass7811(name:String, age:Int) {
    def print()={
      println(name, age)
    }
    def this() {
      this("lisi",20)
    }
    def this(name:String) {
      this(name,20)
    }
  }

  object MyClass7811 {
    def main(args: Array[String]): Unit = {
      var m7811 = new MyClass7811("zhangsan",18)
      m7811.print()
      var m78112 = new MyClass7811()
      m78112.print()
    }
  }

  class MyClass7813 {
    val name = "zhangsan"
    def f1 = {
      println("13")
    }
  }

  abstract  class MyClass7814{
    val name = "李四"
    def f1 = {
      123
    }
  }

  class MyClass7815 extends MyClass7814{
//    override def name:String ={
//      "123"
//    }
    override val f1 = 10
  }
  object MyClass7814 {
    def main(args: Array[String]): Unit = {
//      var t1 = new MyClass7814()
//      println(t1.name)
//      t1.f1
    }
  }

}

package p785{

  import scala.collection.mutable.ArrayBuffer

  object MyClass1 {
    def main(args: Array[String]): Unit = {
      var a1 = new Array[Int](10)
      var a2 = Array(1,2,3,4,"a")
      println(a2(0))
      println(a2)
      println(a2.toList)

      for(a<-a2){
        println(a,"")
      }

      a2.foreach(print)
      a2.foreach(print(_))
      a2.foreach(x=>print(x))
      a2.map(print)
      a2.map(print(_))
      a2.map(x=>print(x))

      //.csv
      //k1:v1,k2:v2,...

      println(a2.mkString(","))

      println(a2.length)
      println(a2.size)

      var a3 = ArrayBuffer(1,2,3,4,5)
      a3.append(6,7)
      a3.foreach(print)
      a3 += (8,9)
      a3.foreach(print)
      println
      a3.remove(0)
      a3.foreach(print)
      println
      a3 -= (4,5,6)
      a3.foreach(print)
      println

      println(a3.toList)
      println(a3.toArray)

    }
  }
}









