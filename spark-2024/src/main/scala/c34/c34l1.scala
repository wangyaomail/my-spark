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
  val file = sc.textFile(localPath+"/input/students_10w.data")
    .map(_.trim.split("\t"))
    .filter(_.length==8)
//  file.foreach(println)
}

object 男女生人数 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(3), 1))
      .groupByKey()
      .mapValues(x=>x.size)
      .foreach(println)
  }
}

object 及格和不及格 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    println("及格人数",file.filter(_(7).toInt>=90).count())
    println("不及格人数",file.filter(_(7).toInt<90).count())
  }
}

object 求出生年月日最大值 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    println("最大值",file.map(_(4).replace("-","").toInt).max)
  }
}

object 手机号 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    println("最大值",file.map(_(5).toLong).filter(_%2==1).max)
  }
}

object 求学生中出现的所有姓氏 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(_(0).substring(0,1)).distinct().foreach(println)
  }
}

object 不同的出生月份 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(_(4).substring(5,7)).distinct().foreach(println)
  }
}

object 求最高分最低分 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    println("最高分",file.map(_(7).toInt).max)
    println("最低分",file.map(_(7).toInt).min)
  }
}

object 平均年龄 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val ageSum = file.map(2025.0 - _(4).substring(0,4).toDouble)
      .sum()
    println("平均年龄", ageSum/file.count())
  }
}

object 平均手机号 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val ageSum = file.map(_(5).substring(0,5).toDouble)
      .sum()

    println("平均手机号", ageSum/file.count())
  }
}

object 年龄分布 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(2024 - _(4).substring(0,4).toInt)
      .map((_,1))
      .groupByKey()
      .map(x=>(x._1, x._2.sum))
      .foreach(println)
  }
}

object 电话号码排序 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(5).toLong,x(0)))
      .sortByKey(false)
      .foreach(println)
  }
}

object 门牌号排序 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(6).substring(6,7).toInt,x(0)))
      .sortByKey(false)
      .foreach(println)
  }
}

object 索引生日 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(4).substring(5), x(0)))
      .groupByKey
      .mapValues(_.toList)
      .foreach(println)
  }
}

object 生日最大5人 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(4).substring(5).replace("-","").toInt, x(0)))
      .sortByKey(false)
      .take(5)
      .foreach(println)
  }
}

object 手机号内8数量最多的前三个手机号 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(5).split("").toList.filter(_=="8").size, x(5)))
      .sortByKey(false)
      .take(5)
      .foreach(println)
  }
}

