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
