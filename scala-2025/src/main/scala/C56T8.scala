package c56
package t8

import scala.io.Source

class StudentsBase {
  var data =Source.fromFile("input/students_10w.data")
    .getLines()
    .toList
    .map(_.trim.split("\t").toList)
}

object 求学生的年龄分布 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(2025-x(4).substring(0,4).toInt, 1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

// 求各省人数分布
object 求各省人数分布 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(6).substring(0,3),1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}


object 按照同学们电话号码的大小排序 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>x(5).toLong)
      .sorted
      .reverse
      .foreach(println)
  }
}
//姓张的学生的分数排序
object 姓张的学生的分数排序 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(7).toInt))
      .filter(_._1.substring(0,1).equals("张"))
      .sortBy(_._2)
      .reverse
      .foreach(println)
  }
}

object 索引出相同生日下同学的姓名链表 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4),x(0)))
      .map(x=>(x._1.substring(5), x._2))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).mkString("-")))
      .foreach(println)
  }
}
//索引出相同成绩的同学的姓名链表
object 索引出相同成绩的同学的姓名链表 extends StudentsBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(7)))
//      .map(x=>(x._1.substring(5), x._2))
      .groupBy(_._2)
      .map(x=>(x._1,x._2.map(_._1).mkString("-")))
      .foreach(println)
  }
}

object 求出生日最大的5个同学的名字 extends StudentsBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(4).substring(5).replaceAll("-","").toInt))
      .sortBy(_._2)
      .reverse
      .take(5)
      .foreach(println)
  }
}
//河南的3个最高分的同学名字
object 河南的3个最高分的同学名字 extends StudentsBase {
  def main(args: Array[String]): Unit = {
    println("张三作业专用")
    data.map(x=>(x(0),x(7),x(6)))
      .filter(_._3.startsWith("河南"))
      .sortBy(_._2)
      .reverse
      .take(5)
      .foreach(println)
    println("张三作业专用")
  }
}
