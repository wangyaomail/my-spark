package c56
package t4

import org.apache.spark.SparkContext
import org.apache.spark.sql.{Column, SparkSession}

import java.io.File

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
}

object checkpoint测试 extends LoadHadoop {
  def main(args: Array[String]): Unit = {
    //设置检查点目录，可以是HDFS等文件系统
    sc.setCheckpointDir(".checkpoint")
    val rdd1 = sc.makeRDD(Array("Spark"))
    val rdd2_source = rdd1.map(_+":"+System.currentTimeMillis())
    val rdd2_cache = rdd1.map(_+":"+System.currentTimeMillis())
    val rdd2_check = rdd1.map(_+":"+System.currentTimeMillis())
//    println("-----------没有设置 Checkpoint-----------")
//    rdd2_source.foreach(println)
//    rdd2_source.foreach(println)
//    rdd2_source.foreach(println)
//    println("-----------设置 Cache 后-----------")
//    rdd2_cache.cache()
//    rdd2_cache.foreach(println)
//    rdd2_cache.foreach(println)
//    rdd2_cache.foreach(println)
    println("-----------设置 Checkpoint 后-----------")
    rdd2_check.checkpoint()
    rdd2_check.foreach(println)
    Thread.sleep(1000)
    rdd2_check.foreach(println)
    rdd2_check.foreach(println)
    rdd2_check.foreach(println)
    rdd2_check.foreach(println)
    sc.stop()
  }
}


object 累加器 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    var a = 1
    var b = List(1,2,3,4)
    b.foreach(i=>{
      a = a+1
    })
    println("a",a)
    // rdd
    var c = sc.longAccumulator("acc")
    sc.makeRDD(b)
      .map(i=>{
        c.add(1)
        i
      }).foreach(
        println("c0",_,c)
      )
    println("c最后",c)
  }
}

object 无广播变量 extends LoadHadoop {
  def main(args: Array[String]): Unit = {
    val list = List((1,1),(2,2),(3,3),(4,4),(5,5),(6,6))
    val rdd1 = sc.makeRDD(List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f")))
    val rdd2 = sc.makeRDD(list)
    val result = rdd1.join(rdd2)
    result.foreach(println)
    sc.stop()
  }
}

object 有广播变量 extends LoadHadoop {
  def main(args: Array[String]): Unit = {
    val list = List((1,3),(2,4),(3,6),(4,6),(5,6),(6,6))
    val rdd = sc.makeRDD(List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f")))
    // 广播变量
    val broad = sc.broadcast(list)
    val result = rdd.map{
      case (key, value) => {
        var e:Any = null
        for (ele <- broad.value){
          if(ele._1 == key){
            e = ele._2
          }
        }
        (key,(value,e))
      }
    }
    result.foreach(println)
    sc.stop()
  }
}

class LoadSparkSQL {
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
}

object 统计男生和女生的总人数 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    data.groupBy("gender").count().show()

    ss.sql(
      """
         select
            gender,
            count(gender) as num
         from students
         group by gender
      """).show()

  }
}

object 统计各班级男女生数量 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    data.groupBy("gender").count().show()

    ss.sql(
      """
         select
            cls,
            gender,
            count(*) as num
         from students
         group by cls, gender
      """).show()

  }
}

object 求出生年月日最大值 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """select
            max(birthday),
            min(birthday)
         from students
      """).show()
  }
}

object 求电话号码为偶数的同学的平均分 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """select
            avg(score)
         from students
         where
            phone %2 =0
      """).show()
  }
}

object 求学生中出现的所有姓氏 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """select
            distinct(substr(name,1,1))
         from students
      """).show()
  }
}

object 求家住洛阳的同学来自哪些班 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """select
            distinct(cls)
         from students
         where substr(loc,4,2) = '洛阳'
      """).show()
  }
}

object 平均年龄 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """select
            avg(2025-int(substr(birthday,0,4)))
         from students
      """).show()
  }
}

object 求学生的年龄分布 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """select
            t.age,
            count(*)
         from
         (
            select
              2025-int(substr(birthday,0,4)) as age
            from students
         ) t
         group by t.age
      """).show()
  }
}

object 求生日各月的分布 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """select
            t.m,
            count(*)
         from
         (
            select
              substr(birthday,6,2) as m
            from students
         ) t
         group by t.m
      """).show()
  }
}

object 按照同学们电话号码的大小排序 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """
         select phone,name
         from students
         order by phone desc
      """).show()
  }
}

object 按手机号后4位排序输出姓名和手机号 extends LoadSparkSQL{
  def main(args: Array[String]): Unit = {
    ss.sql(
      """
         select phone,name
         from students
         order by right(phone,4) desc
      """).show()
  }
}








