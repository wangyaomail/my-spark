package c78

import org.apache.spark.SparkContext

import java.io.File

object WordCountScala {
  def main(args: Array[String]): Unit = {
    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2";
    System.setProperty("hadoop.home.dir", hadoop_home);
    System.load(hadoop_home + "/bin/hadoop.dll");

    val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
    val localProjectPath = new File("").getAbsolutePath()
    val result = sc.textFile(localProjectPath + "/input/books/the_old_man_and_the_sea.txt")
      .flatMap(_.trim.split(" "))
      .map((_, 1))
      .reduceByKey(_+_)
//      .collect()
//    result
//      .foreach(println)
  }
}

class StuBase {
  val sc = new SparkContext("local[*]", "myapp", System.getenv("SPARK_HOME"))
  val localProjectPath = new File("").getAbsolutePath()
  val data = sc.textFile(localProjectPath + "/input/students_10w.data")
    .map(_.trim.split("\t").toList)
    .filter(_.length==8)
}

object 统计男女生人数 extends StuBase {
  def main(args: Array[String]): Unit = {
//    data.map(x=>(x(3),1))
//      .groupByKey()
//      .mapValues(x=>x.size)
//      .foreach(println)

    data.map(x=>(x(3),1))
      .reduceByKey(_+_)
      .foreach(println)
  }
}

object 统计门牌号是奇数和偶数的人数 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(6).takeRight(4).take(3),1))
      .map(x=>(if(x._1.toInt % 2==0) "偶数" else "奇数",1))
      .reduceByKey(_+_)
      .foreach(println)
  }
}

object 出生年月日最大值 extends StuBase{
  def main(args: Array[String]): Unit = {
//    val d = data.map(x=>x(4).replaceAll("-",""))
//    println(d.max, d.min)

    data.map(x=>(x(4),1))
      .sortBy(_._1, false)
      .take(3)
      .foreach(println)
  }
}



object 求手机号中数字7最多的同学名字 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).split("").filter(_=="7").size,x(0)))
      .sortBy(_._1, false)
      .take(10)
      .foreach(println)
  }
}


object 求所有姓氏 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0).split("")(0),1))
      .groupByKey()
      .map(_._1)
      .foreach(println)
  }
}

object 输出手机号前三位的所有值 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).substring(0,3),1))
      .groupByKey()
      .map(_._1)
      .foreach(println)
  }
}