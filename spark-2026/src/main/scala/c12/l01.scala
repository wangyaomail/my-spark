package c12
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
    result.saveAsTextFile(localProjectPath + "/output/wordcount")
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
    .filter(_.length==8)
}

object 统计男生和女生的总人数 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(3),1))
      .groupByKey()
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 统计手机号是奇数和偶数的人数 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).toLong%2,1))
      .groupByKey()
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 求出生年月日最大值 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4),1))
      .sortByKey(false)
      .take(5)
      .foreach(println)
  }
}

object 求11月出生的成学绩最高的同名字 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(7),(x(4),x(0))))
      .filter(_._2._1.contains("-11-"))
      .sortByKey(false)
      .take(3)
      .foreach(println)
  }
}

object 求学生中出现的所有姓氏 extends BaseStu {
  def main(args: Array[String]): Unit = {
    println(data.map(x=>x(0).substring(0,1))
      .distinct().collect().toList)
  }
}

object 门牌号三位数字和等于10的去重的所有值 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(6).takeRight(4).take(3))
      .filter(_.split("").map(_.toInt).sum==10)
      .distinct()
      .foreach(println)
  }
}

object 平均年龄 extends BaseStu {
  def main(args: Array[String]): Unit = {
    val ages = data.map(x=>2026-x(4).take(4).toInt)
    println(ages.sum().toDouble/ages.count())
  }
}

object 输出各个年龄的成绩均值 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(2026-x(4).take(4).toInt,x(7).toDouble))
      .groupByKey()
      .map(x=>(x._1,x._2.sum/x._2.size))
      .foreach(println)

  }
}

object 求学生的年龄分布 extends BaseStu {
  def main(args: Array[String]): Unit = {
    data.map(x=>(2026-x(4).take(4).toInt,x(7).toDouble))
      .groupByKey()
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}
