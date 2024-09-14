package c34

import scala.io.Source

object c34l1 {
  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("input/books/the_old_man_and_the_sea.txt", "UTF-8").getLines().toArray
    source
      .flatMap(x => x.trim().split(" "))
      .map(x => (x, 1))
      .groupBy(x => x._1)
      .map(x => (x._1, x._2.length))
      .toList.sortBy(x => x._2)
      .foreach(x => print(x))

  }

}

object c34l12 {
  def main(args: Array[String]): Unit = {
    val a:String =  null
//    a = 123
    val b = "123"
//    a = "abc"
//    b = "456"
    println(a)
    println(b)

    val c34l12 = new c34l12
    println(c34l12.name)
    c34l12.name = "lisi"
    println(c34l12.name)

    var c = 100
    var d = 100+10
    println(c + d)

    var e = 10.0
    e = 10.0f
    var f = 10l
    f = 10

    var g=
      """
         abcdefg
         1234567
      """
    println(g)
    g = null

    var h = 100
    var i = 200
    def f1():Unit = {
      println("i am f1")
      h += 10
      i += 20
      100
    }
    h += 1
    i += 2
    f1
    println(h)
    println(i)

    def f2:Int = {
      println("i am f2")
      100
    }
    println(f2)

    var j = 10.0f
    j = 20.0.toFloat
    var k = "12345"
    var l = k.toInt
    println(j, k, l)
    var m = 10l
    m = 10
    println(m)
  }
}
class c34l12{
  var name:String = "zhangsan"
}
