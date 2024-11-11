package c34
package l5

import org.apache.spark.sql.{SaveMode, SparkSession}

import java.io.File
import java.util.Properties

import com.mysql.jdbc.Driver

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val localProjectPath = new File("").getAbsolutePath()
  val ss = SparkSession
    .builder
    .master("local")
    .getOrCreate()
}


object 写入Mysql extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "false")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "123456")
    prop.put("driver","com.mysql.jdbc.Driver")
    df.write
      .mode(SaveMode.Append)
      .jdbc("jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8", "students", prop)
  }
}

object 读取Mysql extends LoadHadoop{
  def main(args: Array[String]): Unit = {
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

object 写入Json extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "false")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    df.write
      .mode(SaveMode.Append)
      .json(localProjectPath + "/input/students.json")
  }
}

object 读取json extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.read
      .json(localProjectPath+"/input/students.json")
      .show()
  }
}

object 写入OrcFile extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "false")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    df.write
      .mode(SaveMode.Append)
      .orc(localProjectPath + "/input/students.orc")
  }
}

object 读取OrcFile extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.read
      .orc(localProjectPath+"/input/students.orc")
      .show()
  }
}

object 写入Parquet extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "false")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    df.write
      .mode(SaveMode.Append)
      .parquet(localProjectPath + "/input/students.parquet")
  }
}

object 读取Parquet extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.read
      .parquet(localProjectPath+"/input/students.parquet")
      .show()
  }
}


















