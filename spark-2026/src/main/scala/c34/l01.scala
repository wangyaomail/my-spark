package c34
package l01

import org.apache.spark.SparkContext

import java.io.File


object WorldCount {
  def main(args: Array[String]): Unit = {
    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
    System.setProperty("hadoop.home.dir", hadoop_home)
    System.load(hadoop_home + "/bin/hadoop.dll")


    val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
    sc.setLogLevel("ERROR")
    val localProjectPath = new File("").getAbsolutePath()
    val result = sc.textFile(localProjectPath + "/input/books/the_old_man_and_the_sea.txt")
      .flatMap(_.trim.split(" "))
      .map((_, 1))
      .reduceByKey(_+_)
    result.foreach(println)
//    result.saveAsTextFile(localProjectPath + "/output/wordcount")
  }
}

class BaseStu {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
  sc.setLogLevel("ERROR")
  val localProjectPath = new File("").getAbsolutePath()
  val data = sc.textFile(localProjectPath + "/input/students_10w.data")
    .map(_.trim.split("\t").toList)
}

object 男女生人数 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(3),1))
      .groupByKey
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 统计手机号是奇数和偶数的人数 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).toLong%2,1))
      .groupByKey
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 求出生年月日最大值 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).replaceAll("-","").toInt,1))
      .sortByKey(false)
      .take(3)
      .foreach(println)
  }
}

object 求11月出生的成绩最高的同学名字 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(4), x(7)))
      .filter(x=>x._2.contains("-11-"))
      .map(x=>(x._3,x._1))
      .sortByKey(false)
      .take(3)
      .foreach(println)
  }
}

object 求学生中出现的所有姓氏 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(0).substring(0,1))
      .distinct()
      .foreach(println)
  }
}

object 门牌号三位数字和等于10的去重的所有值 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(6).takeRight(4).take(3))
      .filter(_.split("").toList.map(_.toInt).sum==10)
      .distinct()
      .foreach(println)
  }
}

object 求平均年龄 extends BaseStu {
  def main(args: Array[String]): Unit = {
    val ages = data.map(2026.0 - _(4).substring(0,4).toInt)
    println(ages.sum/ages.count())
  }
}

object 输出各个年龄的成绩均值 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(2026.0 - x(4).substring(0,4).toInt,x(7).toDouble))
      .groupByKey()
      .map(x=>(x._1,x._2.sum/x._2.size))
      .foreach(println)
  }
}

object 求学生的年龄分布 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(2026.0 - x(4).substring(0,4).toInt,x(7).toDouble))
      .groupByKey()
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 上下半月出生的学生分布 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).split("-")(2).toInt,1))
      .map(x=>(if(x._1<=15) "上半月" else "下半月",1))
      .groupByKey()
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

