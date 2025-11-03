package c56

import org.apache.spark.SparkContext

import java.io.File

object WordCountScala {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local","")
    val filePath = new File("").getAbsolutePath
    sc.textFile(filePath+"/input/books/the_old_man_and_the_sea.txt")
      .flatMap(x=>x.split(" "))
      .map((_,1))
      .groupByKey()
      .map(x=>(x._1, x._2.size))
      .sortBy(_._2,false)
//      .take(10)
      .collect()
//      .foreach(println)
  }
}

object 统计男生和女生的总人数 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local","")
    val filePath = new File("").getAbsolutePath
    sc.textFile(filePath+"/input/students_10w.data")
      .map(x=>x.split("\t").toList)
      .filter(_.size==8)
  }
}

class StuBase{
//  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
//  System.setProperty("hadoop.home.dir", hadoop_home)
//  System.load(hadoop_home + "/bin/hadoop.dll")

  val sc = new SparkContext("local","")
  val filePath = new File("").getAbsolutePath
  val data = sc.textFile(filePath+"/input/students_10w.data")
    .map(x=>x.split("\t").toList)
    .filter(_.size==8)
}

object 男女生人数 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(3),1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 门牌号是奇数和偶数的人数 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>x(6).takeRight(4).take(3))
      .map(x=>if(x.toInt % 2 ==0) "偶数" else "奇数")
      .map((_,1))
      .reduceByKey(_+_)
      .foreach(println)
  }
}

object 求出生年月日最大值 extends StuBase{
  def main(args: Array[String]): Unit = {
    val d = data.map(_(4).replaceAll("-","").toInt)
//    println(d.max, d.min)
    val e = d.map((_,1))
      .sortBy(_._1,false)
    println(e.first())
    println(e.sortBy(_._1,true).first())
  }
}

object 手机号中数字7最多的同学名字 extends StuBase{
  def main(args: Array[String]): Unit = {
    val d = data.map(x=>(x(5).split("").filter(_=="7").toList.size,x(0)))
    println(d.max)
  }
}
object 求学生中出现的所有姓氏 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>x(0).substring(0,1))
      .distinct()
      .foreach(println)
  }
}

object 求平均年龄 extends StuBase{
  def main(args: Array[String]): Unit = {
    // 性能较差
//    var d2 = data.map(2025-_(4).substring(0,4).toInt)
//    println("平均年龄",d2.sum/d2.count)
    // 性能好一些
    val d = data.map(x=>(2025-x(4).substring(0,4).toInt, 1))
      .reduce((x,y)=>(x._1+y._1, x._2+y._2))
    println(d._1.toDouble/d._2)
  }
}

object 成绩均值 extends StuBase{
  def main(args: Array[String]): Unit = {
    val d = data.map(x=>(x(7).toInt, 1))
      .filter(x=>x._1>=60 && x._1<80)
      .reduce((x,y)=>(x._1+y._1, x._2+y._2))
    println(d._1.toDouble/d._2)
  }
}

object 求学生的年龄分布 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(2025-x(4).substring(0,4).toInt,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 求学生的月份分布 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).substring(5,7).toInt,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 电话大小排序 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).toLong,1))
      .sortBy(_._1, false)
      .foreach(println)
  }
}

object 按学号后四位生日的数字和排序名字 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(2).takeRight(4).toInt+x(4).substring(5).replaceAll("-","").toInt,x(0)))
      .sortBy(_._1, false)
      .foreach(println)
  }
}

object 索引生日姓名链表 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).substring(5),x(0)))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2)))
      .sortBy(_._1)
      .foreach(println)
  }
}


object 索引河南各市的各区链表 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(6).take(6),x(6).substring(0,x(6).length-4)))
      .filter(x=>x._1.startsWith("河南省"))
      .filter(x=>x._1.length!=x._2.length)
      .map(x=>(x._1.takeRight(3), x._2.takeRight(3)))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).toList.distinct))
      .sortBy(_._1)
      .foreach(println)
  }
}

object 求出生日最大的5个同学的名字 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).substring(5).replaceAll("-","").toInt,x(0)))
      .sortBy(_._1,false)
      .take(5)
      .foreach(println)
  }
}

object 求手机号按位加和最大的三个手机号 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).map(_.toInt-'0').sum,x(5)))
      .sortBy(_._1,false)
      .take(5)
      .foreach(println)
  }
}


