package c56
package l6

object MyObject1{
  def main(args: Array[String]): Unit = {
    val data = scala.io.Source
      .fromFile("input/students.data","utf8")
      .getLines().toList
    data.foreach(println)
    data.map(_.trim().split("\t"))
      .filter(_.length==8)
      .map(x=>(x(3),1))
      .groupBy(_._1)
      .mapValues(_.map(_._2).sum)
      .foreach(println)
  }
}

object MyObject2{
  def main(args: Array[String]): Unit = {
    val data = scala.io.Source
      .fromFile("input/students.data","utf8")
      .getLines().toList
    var data2 = data.map(_.trim().split("\t"))
      .filter(_.length==8)
      .map(_(4).replace("-", "").toInt)
    println(data2.max)
    println(data2.min)
  }
}

object MyObject3{
  def main(args: Array[String]): Unit = {
    Array.tabulate(9)(i=>((i+1)*10,(i+2)*10))
      .foreach(println)
  }
}