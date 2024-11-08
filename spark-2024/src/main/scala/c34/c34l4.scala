package c34
package l4

import org.apache.spark.sql.SparkSession

import java.io.File

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val ss = SparkSession.builder().master("local").getOrCreate()
  ss.sparkContext.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath
  val data = ss.read.schema("name STRING, no STRING, cls STRING, gender STRING," +
                  " birthday STRING, phone STRING, loc STRING")
    .option("header","false")
    .csv(localPath+"/input/students.csv")
  data.show()
  data.describe().show()
  data.createTempView("students")
}

object 空的 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
  }
}

object 男女生总人数 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    // 函数式
    data.groupBy("gender").count().show()
    // sql
    ss.sql("select gender, count(gender) as count from students group by gender").show()
  }
}

object 统计各班级人数 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    // 函数式
    data.groupBy("cls").count().show()
    // sql
    ss.sql("select cls, count(cls) as count from students group by cls").show()
  }
}
object 出生年月日最大最小值 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    // 函数式
    println(data.sort("birthday").first())
    println(data.sort(data("birthday").desc).first())
    // sql
    ss.sql("select min(birthday),max(birthday) from students").show()
  }
}

object 电话号码为奇数的最大值 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select max(phone) from students where phone %2 =1").show()
  }
}

object 求学生中出现的所有姓氏 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select distinct substr(name, 0,1) from students").show()
  }
}

object 求所有人不同的出生月份 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select distinct substr(birthday, 6,2) from students").show()
  }
}

object 求平均年龄 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select int(avg(2024 - substr(birthday, 0,4))) as avgage from students").show()
  }
}

object 求手机号前五位的均值 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select avg(substr(phone,0,5)) from students").show()
  }
}

object 求学生的年龄分布 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select age, count(age) from (select 2024 - substr(birthday, 0,4) as age from students) group by age").show()
  }
}

object 求手机号前三位的分布 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select substr(phone, 0, 3) as phone3, count(*) from students group by phone3").show()
  }
}

object 按照同学们电话号码的大小排序 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select phone from students order by phone").show()
  }
}

object 按住址门牌号排序输出学生名字和住址 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    ss.sql("select name,loc from students order by substr(loc,7,1)").show()
  }
}

