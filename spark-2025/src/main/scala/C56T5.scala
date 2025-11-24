package c56
package t5

import org.apache.spark.sql.functions.{rand, randn}
import org.apache.spark.sql.{Column, SaveMode, SparkSession}

import java.io.File
import java.util.Properties

class LoadSparkSQL {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  var ss = SparkSession.builder()
    .master("local")
    .getOrCreate()
  ss.sparkContext.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath
  val data = ss.read
    .schema("name STRING," +
      " no STRING," +
      " cls STRING," +
      " gender STRING," +
      " birthday STRING," +
      " phone STRING," +
      " loc STRING," +
      " score STRING")
    .option("header","false")
    .csv(localPath+"/input/students.csv")
  data.createTempView("students")
  data.show()
  val localProjectPath = new File("").getAbsolutePath()

}

object DFF extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    val d = ss.range(0, 10)
      .select(new Column("id"),
        rand(seed = 10).alias("uniform"),
        randn(seed = 27).alias("normal"))
      d.show()
      d.describe().show()
  }
}

object 协方差相关性 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.range(0, 10)
      .select(
        new Column("id"),
        rand().alias("a"),
        randn().alias("b"))

//    println(df.stat.cov("a", "b"))
//    println(df.stat.cov("a", "a"))

    println(df.stat.corr("a", "b"))
    println(df.stat.corr("a", "a"))

  }
}

object 写入Mysql extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "true")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    import com.mysql.jdbc.Driver
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "123456")
    prop.put("driver","com.mysql.jdbc.Driver")
    df.write
      .mode(SaveMode.Append)
      .jdbc("jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8", "students", prop)
  }
}

object 读取Mysql extends LoadSparkSQL{
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


