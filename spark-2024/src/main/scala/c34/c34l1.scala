package c34
package l1

import org.apache.spark.SparkContext

import java.io.File

object myobj1 {
  def main(args: Array[String]): Unit = {
    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
    System.setProperty("hadoop.home.dir", hadoop_home)
    System.load(hadoop_home + "/bin/hadoop.dll")

    val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
    sc.setLogLevel("ERROR")
    val localPath = new File("").getAbsolutePath()
    val file = sc.textFile(localPath+"/input/books/the_old_man_and_the_sea.txt")
    file.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .sortBy(_._2, false)
      .foreach(println)
  }
}

class LoadSpark{
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
  sc.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath()
  val file = sc.textFile(localPath+"/input/students.data")
    .map(_.trim.split("\t"))
    .filter(_.length==8)
  file.foreach(println)
}

object 男女生人数 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(3), 1))
      .groupByKey()
      .mapValues(x=>x.size)
      .foreach(println)
  }
}


