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

class LoadData {
  val data =scala.io.Source
    .fromFile("input/students.data")
    .getLines().toList
    .map(_.trim().split("\t"))
    .filter(_.length==8)
}

object obj3 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(_(0).substring(0,1))
      .distinct
      .foreach(println)
  }
}

object obj4 extends LoadData{
  def main(args: Array[String]): Unit = {
    val ages = data.map(2024 - _(4).substring(0,4).toInt)
    val avgage = ages.reduce(_+_)/ages.length
    println(ages.size)
  }
}

object obj5 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(2024 - _(4).substring(0,4).toInt)
      .map(x=>(x, 1))
      .groupBy(_._1)
      .mapValues(_.map(_._2).length)
      .foreach(println)
  }
}

object obj6 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(_(5).toLong)
      .sorted
      .reverse
      .foreach(println)

  }
}

object obj7 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).substring(5),x(0)))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.map(_._2)))
      .foreach(println)
  }
}

object obj8 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(_(4).replace("-","").toInt)
      .sorted
      .reverse
      .take(5)
      .foreach(println)
  }
}