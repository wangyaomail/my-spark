package c34
package l6

object obj1{
  def main(args: Array[String]): Unit = {
    val data =scala.io.Source
      .fromFile("input/students.data")
      .getLines().toList
    data.map(_.trim().split("\t"))
      .filter(_.length==8)
      .map(x=>(x(3), 1))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.length))
      .foreach(println)
  }
}

object obj2{
  def main(args: Array[String]): Unit = {
    val data =scala.io.Source
      .fromFile("input/students.data")
      .getLines().toList
    val data2 = data.map(_.trim().split("\t"))
      .filter(_.length==8)
      .map(_(4).replace("-","").toInt)
    println(data2.max)
    println(data2.min)
  }
}