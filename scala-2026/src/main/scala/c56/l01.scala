package abc

import scala.io.Source

class c56l0101 {
}

object c56l0101 {
  def main(args: Array[String]): Unit = {
    println("hello world BBB")
  }
}

class c56l0102 {
  def main(args: Array[String]): Unit = {
    println("hello world AAA")
  }
}
class c56l0103 {

}

object XXX {
  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("input/books/the_old_man_and_the_sea.txt", "UTF-8").getLines().toArray
    source
      .flatMap(x => x.trim().split(" "))
//      .map(x=>x+",")
      .map(x => (x, 1))
      .groupBy(x => x._1)
      .map(x => (x._1, x._2.length))
      .toList.sortBy(x => x._2)
      .foreach(x => println(x))
  }
}

object c56l0104 {
  def main(args: Array[String]): Unit = {
//    var a = "123"
//    var ++ = "123"
//    var `+a` = "123"
    var a = 123
    val b = 123
    a = 567
    var c = "123"
    var d:String = null
    d = "abc"
    println(a,b,c,d)


  }
}


class Person {
  var name = "张三"
  val age = 20
}
object c56l0105 {
  def main(args: Array[String]): Unit = {
    var p1 = new Person
    val p2 = new Person
    p1 = new Person
//    p2 = new Person
    p1.name = "李四"
    p2.name = "王五"
    println(p1.name,p2.name)
  }
}

object c56l0106 {
  def main(args: Array[String]): Unit = {
    var x =
      """aaa
        |aaa
        |aaa
        |""".stripMargin
    var y =
      """bbb
         bbb
         bbb
      """
    println(x)
    println(y)

    var a:String = null
    a = "123"

    def f1 = {
      "aaa"
    }
    println(f1)
    def f2():Unit = {
      "bbb"
    }
    println(f2)

    var f3 = f1
    def f4 = {
      f1
    }
    var f5 = f4
    println(f5)
  }
}

object c56l0107 {
  def main(args: Array[String]): Unit = {
    var a = 1
    var b = 2L
    println(a,b)
    a=b.toInt
    b=a
    println(a,b)

    var c = 1.0f
    var d = 2.0
    c=d.toFloat
    d=c
  }
}


