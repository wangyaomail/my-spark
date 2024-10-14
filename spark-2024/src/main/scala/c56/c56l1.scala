package c56
package l1

import c56.l1.出生年月日最大.file
import org.apache.spark.SparkContext

import java.io.File

object WordCount{
  def main(args: Array[String]): Unit = {
    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
    System.setProperty("hadoop.home.dir", hadoop_home)
    System.load(hadoop_home + "/bin/hadoop.dll")

    val sc = new SparkContext("local", "app", System.getenv("SPARK_HOME"))
    sc.setLogLevel("ERROR")
    val localPath = new File("").getAbsolutePath
    val file = sc.textFile(localPath+"/input/books/the_old_man_and_the_sea.txt")
    file.flatMap(_.split(" "))
      .map((_,1))
      .groupByKey()
      .map(x=>(x._2.size, x._1))
      .sortByKey(false)
      .foreach(println)

  }

}


class LoadSpark{
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")

  val sc = new SparkContext("local", "app", System.getenv("SPARK_HOME"))
  sc.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath
  val file = sc.textFile(localPath+"/input/students.data")
     .map(_.split("\t"))
     .filter(_.length == 8)

  file.collect().map(_.toList).foreach(println)
}
object 男女生人数 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    file
      .map(x=>(x(3),1))
      .groupByKey()
      .map(x=>(x._1, x._2.size))
      .foreach(println)
  }
}

object 及格不及格 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    println("及格", file.map(x=>x(7).toInt).filter(_>=60).count())
    println("不及格", file.map(x=>x(7).toInt).filter(_<60).count())
  }
}

object 出生年月日最大 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    println("最大值",file.map(x=>x(4).replace("-","").toLong).max)
  }
}

object 电话号码为奇数的最大值 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    println("最大值",file.map(x=>x(5).toLong).filter(_%2==1).max)
  }
}

object 所有姓氏 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>x(0).substring(0,1))
      .distinct()
      .foreach(println)
  }
}


object 月份去重 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>x(4).substring(5,7).toInt)
      .distinct()
      .foreach(println)
  }
}

object 平均年龄 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val ageSum = file.map(x=>2024.0 - x(4).substring(0,4).toDouble).sum()
    println(ageSum/file.count())
  }
}

object 平均手机前五位 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val ageSum = file.map(x=>x(5).substring(0,5).toDouble).sum()
    println(ageSum/file.count())
  }
}

object 年龄分布 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>2024 - x(4).substring(0,4).toInt)
      .map((_,1))
      .groupByKey()
      .map(x=>(x._1, x._2.size))
      .foreach(println)
  }
}

object 手机前三位分布 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=> x(5).substring(0,3).toInt)
      .map((_,1))
      .groupByKey()
      .map(x=>(x._1, x._2.size))
      .foreach(println)
  }
}


object 电话号码排序 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>x(5).toLong)
      .sortBy(k=>k, false)
      .foreach(println)
  }
}

object 门牌号排序 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    file.map(x=>(x(6).substring(6,7).toInt, x(0), x(6)))
      .sortBy(k=>k._1, false)
      .foreach(println)
  }
}

object 年龄链表 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    file.map(x => (x(4).substring(5),x(0)))
      .groupByKey()
      .map(x=>(x._1, x._2.toList))
      .foreach(println)
  }
}

object 门牌号链表 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    file.map(x => (x(6).substring(6,7).toInt,x(0)))
      .groupByKey()
      .map(x=>(x._1, x._2.toList))
      .foreach(println)
  }
}

object 生日最大五个 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    file.map(x => (x(4).substring(5).replace("-","").toInt,x(0)))
      .sortBy(x=>x._1,false)
      .take(5)
      .foreach(println)
  }
}

object 手机号8最多 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    file.map(x => (x(5).split("").filter(_=="8").size,x(5)))
      .sortBy(x=>x._1,false)
      .take(5)
      .foreach(println)
  }
}


