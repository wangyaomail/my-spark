import scala.io.Source

object l0101 {
  def main(args: Array[String]): Unit = {
    println("hello world")
    val source = Source.fromFile("input/books/the_old_man_and_the_sea.txt", "UTF-8").getLines().toArray
    source
      .flatMap(x => x.trim().split(" "))
//      .map(x=>x+",")
      .map(x => (x, 1))
      .groupBy(x => x._1)
      .map(x => (x._1, x._2.length))
      .toList.sortBy(x => x._2)
      .foreach(x => print(x))
  }
}

class l0102 {
  var x=1

}

object l0102 {
  def main(args: Array[String]): Unit = {
    var x=1
    var y =2
    var z =3; var m=4
    var ++ = 1
//    var + = 2
//    var +a = 3
    var `+a` = 3

    var a = 1
    val b = 1
    a=2
//    b=2
    println(a, b)

    var c = 1l
    c = a
    var d = 1.0f
    d=a
    a=d.toInt
    a=c.toInt

    var e:String = null
    e="asd"


  }
}

class Person {
  var name = "张三"
  val age = 20
}
object l0103 {
  def main(args: Array[String]): Unit = {
    var zs1 = new Person
    zs1 = new Person
    println(zs1.name,zs1.age)
    val zs2 = new Person
    println(zs2.name, zs2.age)
//    zs2 = new Person
    zs2.name = "lisi"
//    zs2.age = 21
    println(zs2.name, zs2.age)
  }
}

object l0104 {
  def main(args: Array[String]): Unit = {
    var x = "hahaha"
    var y =
      """
        |yyyyy
        |yyyyy
        |yyyyy
        |""".stripMargin
    var z =
      """
         zzzzz
         zzzzz
         zzzzz
      """
    println(x)
    println(y)
    println(z)
  }
}

object l0105 {
  def main(args: Array[String]): Unit = {
    var x = "hahaha"
    x=null
    var y = 123
//    y=null
//    println(x,y)

    def f1():Int = {
      println("bbbb")
      "123".toInt
    }
//    println(f1)
    var f2 = {
      "aaa"
      "bbb"
      println("aaaa")
      "345"
    }
    var f3 = f1
//    println(f2)

  }
}