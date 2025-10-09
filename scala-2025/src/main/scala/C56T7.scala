package c56
package t7

import scala.io.Source

object 男女生人数 {
  def main(args: Array[String]): Unit = {
    var data =Source.fromFile("input/students_10w.data")
      .getLines()
      .toList
      .map(_.trim.split("\t").toList)
//    data.foreach(println)
    data.groupBy(_(3))
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

class StudentsBase {
  var data =Source.fromFile("input/students_10w.data")
    .getLines()
    .toList
    .map(_.trim.split("\t").toList)
}

object 求出生年月日最大值 extends StudentsBase {
  def main(args: Array[String]): Unit = {
    println(data.map(_(4)).max)
    println(data.map(_(4))
      .map(_.split("").filter(_!="-").toList.mkString(""))
      .map(_.toInt)
      .max
    )
  }
}

object 求学生中出现的所有姓氏 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    println(data.map(_(0).substring(0,1))
      .distinct
      .mkString(""))
    println(data.map(x=>(x(0).substring(0,1),1))
      .groupBy(_._1)
      .map(_._1)
      .mkString(""))
  }
}

object 哪些省份 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(_(6).substring(0,3))
      .distinct
      .foreach(println)
  }
}

object 平均年龄 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    var d2 = data.map(2025-_(4).substring(0,4).toInt)
    println("平均年龄",d2.sum/d2.size)
  }
}

object 平均门牌 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    var d2 =data.map(_(6).takeRight(4).take(3).toInt)
    println(d2.sum/d2.length)
  }
}

object 求学生的年龄分布 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(2025-x(4).substring(0,4).toInt,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}