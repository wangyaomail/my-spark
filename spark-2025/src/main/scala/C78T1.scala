package c78

import org.apache.spark.SparkContext

import java.io.File

object WordCountScala {
  def main(args: Array[String]): Unit = {
    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2";
    System.setProperty("hadoop.home.dir", hadoop_home);
    System.load(hadoop_home + "/bin/hadoop.dll");

    val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
    val localProjectPath = new File("").getAbsolutePath()
    val result = sc.textFile(localProjectPath + "/input/books/the_old_man_and_the_sea.txt")
      .flatMap(_.trim.split(" "))
      .map((_, 1))
      .reduceByKey(_+_)
//      .collect()
//    result
//      .foreach(println)
  }
}

class StuBase {
  val sc = new SparkContext("local[*]", "myapp", System.getenv("SPARK_HOME"))
  val localProjectPath = new File("").getAbsolutePath()
  val data = sc.textFile(localProjectPath + "/input/students_10w.data")
    .map(_.trim.split("\t").toList)
    .filter(_.length==8)
}

object 统计男女生人数 extends StuBase {
  def main(args: Array[String]): Unit = {
//    data.map(x=>(x(3),1))
//      .groupByKey()
//      .mapValues(x=>x.size)
//      .foreach(println)

    data.map(x=>(x(3),1))
      .reduceByKey(_+_)
      .foreach(println)
  }
}

object 统计门牌号是奇数和偶数的人数 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(6).takeRight(4).take(3),1))
      .map(x=>(if(x._1.toInt % 2==0) "偶数" else "奇数",1))
      .reduceByKey(_+_)
      .foreach(println)
  }
}

object 出生年月日最大值 extends StuBase{
  def main(args: Array[String]): Unit = {
//    val d = data.map(x=>x(4).replaceAll("-",""))
//    println(d.max, d.min)

    data.map(x=>(x(4),1))
      .sortBy(_._1, false)
      .take(3)
      .foreach(println)
  }
}



object 求手机号中数字7最多的同学名字 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).split("").filter(_=="7").size,x(0)))
      .sortBy(_._1, false)
      .take(10)
      .foreach(println)
  }
}


object 求所有姓氏 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0).split("")(0),1))
      .groupByKey()
      .map(_._1)
      .foreach(println)
  }
}

object 输出手机号前三位的所有值 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).substring(0,3),1))
      .groupByKey()
      .map(_._1)
      .foreach(println)
  }
}

object 求平均年龄 extends StuBase{
  def main(args: Array[String]): Unit = {
    // 性能较差
//    var d2 = data.map(2025-_(4).substring(0,4).toInt)
//    println("平均年龄",d2.sum()/d2.count())
    // 性能较好
    val d = data.map(x=>(2025-x(4).substring(0,4).toInt, 1))
      .reduce((x,y)=>(x._1+y._1,x._2+y._2))
    println(d, d._1.toDouble/d._2)

  }
}

object 输出6080分数段的成绩均值 extends StuBase{
  def main(args: Array[String]): Unit = {
    val d = data.map(x=>(x(7).toInt, 1))
      .filter(x=>x._1>=60 & x._1<80)
      .reduce((x,y)=>(x._1+y._1,x._2+y._2))
    println(d, d._1.toDouble/d._2)
  }
}

object 求学生的年龄分布 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(2025-x(4).substring(0,4).toInt, 1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 求学生的月份分布 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).substring(5,7).toInt, 1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 按电话号码排序 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).toLong, 1))
      .sortBy(_._1)
      .foreach(println)
  }
}

object 复杂排序 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(2).substring(5).toInt, x(4).takeRight(5).replaceAll("-","") , x(0)))
      .map(x=>(x._1+x._2.toInt, x._3))
      .sortBy(_._1)
      .foreach(println)
  }
}

object 索引生日相同姓名 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).takeRight(5), x(0)))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).mkString("、")))
      .foreach(println)
  }
}

object 索引河南省各市各区 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>x(6))
      .filter(_.contains("河南省"))
      .map(x=>(x.substring(3,6),x.substring(6,9) ))
//      .filter(x=> ! (x._2.takeRight(0)>="0" & x._2.takeRight(0)<="9"))
      .filter(x=>x._2.contains("县") || x._2.contains("区"))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).toList.distinct))
      .foreach(println)
  }
}
object 求出生日最大的5个同学的名字 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(4).substring(5)))
      .sortBy(_._2,false)
      .take(5)
      .foreach(println)
  }
}

object 求手机号按位加和最大的三个手机号 extends StuBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5),x(5).map(_.toInt-'0').sum))
      .sortBy(_._2,false)
      .take(3)
      .foreach(println)
  }
}

