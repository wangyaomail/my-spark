package c78
package t7

import scala.io.Source

object 统计男生和女生的总人数 {
  def main(args: Array[String]): Unit = {
    Source.fromFile("input/students_10w.data")
      .getLines()
      .toList
      .map(_.trim.split("\t").toList)
      .map(x=>(x(3),1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

class StudentsBase {
  var data = Source.fromFile("input/students_10w.data")
    .getLines()
    .toList
    .map(_.trim.split("\t").toList)
}

object 统计男生和女生的总人数2 extends StudentsBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(3),1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 求出生年月日最大值 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    println(data.map(2025-_(4).substring(0,4).toInt).max)
  }
}

object 求学生中出现的所有姓氏 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(_(0).substring(0,1))
      .distinct
      .foreach(println)
    data.map(x=>(x(0).substring(0,1),1))
      .groupBy(_._1)
      .map(_._1)
      .foreach(println)
  }
}


object 学生来自哪些省份 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(_(6).substring(0,3))
      .distinct
      .foreach(println)
  }
}

object 求平均年龄 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    var age = data.map(2025-_(4).substring(0,4).toInt)
    println(age.sum/age.size)
  }
}

object 求平均门牌号 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    var no = data.map(_(6).takeRight(4).take(3).toInt)
    println(no.sum/no.size)
  }
}