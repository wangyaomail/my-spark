package c78

import c56.t5.LoadSparkSQL
import c56.t5.写json.data
import c56.t5.写orc.data
import c56.t5.写parquet.data
import c56.t5.读json.ss

import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.{rand, randn}
import org.apache.spark.sql.{Column, SaveMode, SparkSession}

import java.io.File
import java.util.Properties

class LoadSparkSession {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")


  val ss = SparkSession.builder()
    .master("local")
    .getOrCreate()
  ss.sparkContext.setLogLevel("ERROR")
  val data = ss.read
    .schema("name STRING," +
      " no STRING, " +
      "cls STRING, " +
      "gender STRING, " +
      "birthday STRING, " +
      "phone STRING, " +
      "loc STRING," +
      "score STRING")
    .option("header","false")
    .csv("C:\\nos\\my-spark\\input\\students.csv")
  data.createTempView("students")
  data.show()
}

object 随机数 extends LoadSparkSession {
  def main(args: Array[String]): Unit = {
    val rr = ss.range(0, 10)
      .select(new Column("id"),
        rand().alias("uniform"),
        randn().alias("normal"))
    rr.show()
    rr.describe().show()
  }
}

object 协方差 extends LoadSparkSession{
  def main(args: Array[String]): Unit = {
    val df = ss.range(0, 10)
      .select(
        new Column("id"),
        rand().alias("a"),
        randn().alias("b"))
    println(df.stat.cov("a", "b"))
    println(df.stat.cov("a", "a"))
    println(df.stat.corr("a", "b"))
    println(df.stat.corr("a", "a"))
    val dftest = ss.range(0,1000000)
      .select(
        new Column("id"),
        rand().alias("a"),
        rand().alias("b"))
    println(df.stat.corr("a", "b"))
  }
}

object 写入Mysql extends LoadSparkSession{
  def main(args: Array[String]): Unit = {
    import com.mysql.jdbc.Driver
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "123456")
    prop.put("driver","com.mysql.jdbc.Driver")
    data.write
      .mode(SaveMode.Append)
      .jdbc("jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8", "students", prop)
  }
}

object 读取Mysql extends LoadSparkSession{
  def main(args: Array[String]): Unit = {
    import com.mysql.jdbc.Driver
    val df = ss.read
      .format("jdbc")
      .option("url", "jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "students")
      .option("user", "root")
      .option("password", "123456")
      .load()
    df.show()
  }
}

object 读写JSON extends LoadSparkSession{
  def main(args: Array[String]): Unit = {
    val data = ss.read.json("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
    data.printSchema()
    data.show()
    import com.mysql.jdbc.Driver
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "123456")
    prop.put("driver","com.mysql.jdbc.Driver")
    data.write
      .mode(SaveMode.Append)
      .jdbc("jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8", "qa", prop)
    data.write.json("C:\\share\\data\\webtext2019zh\\aaa.json")
  }
}

object 读json extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    val data = ss.read.json("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
    data.printSchema()
    data.show(10)
  }
}

object 写json extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    data.write.json("C:\\nos\\my-spark\\output\\stujson")
  }
}

object 写orc extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    data.write.orc("C:\\nos\\my-spark\\output\\stuorc")
  }
}

object 写parquet extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    data.write.parquet("C:\\nos\\my-spark\\output\\stup")
  }
}