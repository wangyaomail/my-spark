package c56
package l4

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.io.File

class LoadSpark{
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")

  val ss = SparkSession.builder()
    .master("local").getOrCreate()
  ss.sparkContext.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath
  val data = ss.read.schema("name STRING, no STRING, cls STRING, gender STRING, " +
                            "birthday STRING, phone STRING, loc STRING")
    .option("header", "false")
    .csv(localPath+"/input/students.csv")
  data.createTempView("students")
  data.show()
  data.describe().show()
}

object 男女生总人数 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    // 函数式
    data.groupBy("gender").count().show()
    // sql
    ss.sql("select gender, count(*) as count from students group by gender").show()
  }
}

object 各班人数 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    // 函数式
    data.groupBy("cls").count().show()
    // sql
    ss.sql("select cls, count(*) as count from students group by cls").show()
  }
}

object 出生年月日最大最小值 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    // 函数式
    println(data.sort("birthday").first())
    println(data.sort(data("birthday").desc).first())

    // sql
    ss.sql("select max(birthday), min(birthday) from students").show()
  }
}


object 电话号码为奇数的最大值 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select max(phone) from students where phone%2=1").show()
  }
}

object 所有姓氏 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select distinct substr(name,0,1) from students").show()
  }
}

object 所有人不同的出生月份 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select distinct substr(birthday,6,2) from students").show()
  }
}

object 平均年龄 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select avg(2024-substr(birthday,0,4)) from students").show()
  }
}

object 手机号前五位的均值 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select avg(substr(phone,0,5)) from students").show()
    ss.sql("select avg(int(phone/1000000)) from students").show()
  }
}

object 年龄分布 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select age, count(age) from (select int(2024-substr(birthday,0,4)) as age from students) group by age").show()
  }
}

object 手机号前三位的分布 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select ph3, count(ph3) from (select substr(phone,0,3) as ph3 from students)  group by  ph3").show()
  }
}


object 按照同学们电话号码的大小排序 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select name, phone from students order by phone").show()
    ss.sql("select name, phone from students order by phone desc ").show()
  }
}


object 按住址门牌号排序输出学生名字和住址 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    ss.sql("select name, loc, substr(loc,7,1) as mph from students order by mph").show()
  }
}










