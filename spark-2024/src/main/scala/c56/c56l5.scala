package c56
package l5

import org.apache.spark.sql.{SaveMode, SparkSession}

import java.io.File
import java.util.Properties

class LoadSQL {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val localProjectPath = new File("").getAbsolutePath()
  val ss = SparkSession
    .builder
    .master("local")
    .getOrCreate()
}

object 写入Mysql extends LoadSQL{
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

object 读取Mysql extends LoadSQL{
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

object 写入JSON extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "false")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    df.write
      .mode(SaveMode.Append)
      .json(localProjectPath+"/input/students.json")
  }
}

object 读取JSON extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      .json(localProjectPath+"/input/students.json")
      .show()
  }
}

object 读取JSONPython extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      .json(localProjectPath+"/input/students3.json")
      .show()
  }
}

object 写入OrcFile extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "false")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    df.write
      .mode(SaveMode.Append)
      .orc(localProjectPath+"/input/students.orc")
  }
}

object 读取OrcFile extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      .orc(localProjectPath+"/input/students.orc")
      .show()
  }
}

object 读取OrcFilePython extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      .orc(localProjectPath+"/input/students2.orc")
      .show()
  }
}

object 写入ParquetFile extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      //姓名,学号,班级,性别,生日,手机号,住址
      .schema("name STRING, no STRING, cls STRING, gender STRING, birthday STRING, phone STRING, loc STRING")
      .option("header", "false")
      .csv(localProjectPath + "/input/students.csv")
    df.show()
    df.write
      .mode(SaveMode.Append)
      .parquet(localProjectPath+"/input/students.parquet")
  }
}

object 读取parquet extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      .parquet(localProjectPath+"/input/students.parquet")
      .show()
  }
}

object 读取parquetPython extends LoadSQL{
  def main(args: Array[String]): Unit = {
    val df = ss.read
      .parquet(localProjectPath+"/input/students2.parquet")
      .show()
  }
}