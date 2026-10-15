package c12
package l05

import scala.io.Source


object 统计男生和女生的总人数 {
  def main(args: Array[String]): Unit = {
    val source = scala.io.Source
      .fromFile("input/students_10w.data", "UTF-8")
      .getLines().toArray
    source.map(_.trim().split("\t"))
      .filter(_.length == 8)
      .map(x => (x(3), 1))
      .groupBy(x => x._1)
      .map(x=>(x._1, x._2.length))
      .foreach(println(_))
  }
}

class StuBase {
  val data = scala.io.Source
    .fromFile("input/students_10w.data", "UTF-8")
    .getLines()
    .toArray
    .map(_.trim().split("\t"))
}

object 同学们生日在上半年还是下半年的数量 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(4).substring(5,7).toInt)
      .map(x=>(if( x > 6) "下半年" else "上半年",1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)


    data.map(x=>x(4).takeRight(5).take(2).toInt)
      .map(x=>(if( x > 6) "下半年" else "上半年",1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)

    data.map(x=>x(4).replaceAll("-","").toInt)
      .map(_%10000)
      .map(x=>(if( x > 700) "下半年" else "上半年",1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 求出生年月日最大值 extends StuBase {
  def main(args: Array[String]): Unit = {
    val bd = data.map(x=>x(4).replaceAll("-","").toInt).toList
    println(bd.max, bd.min, bd.sorted.take(1))
  }
}

object 求手机号后8位最大值 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(5).takeRight(8).toInt)
      .sorted.takeRight(1)
      .foreach(println)
  }
}

object 求学生中出现的所有姓氏 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(_(0).substring(0,1))
      .distinct.foreach(println)
  }
}

object 手机号后8位的所有不同的取值 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(5).takeRight(8).toInt)
      .distinct
      .foreach(println)
  }
}

object 求平均年龄 extends StuBase {
  def main(args: Array[String]): Unit = {
    val age = data.map(x=>2026 - x(4).take(4).toInt)
    println(age.sum/age.length)
  }
}


object 计算各班成绩的平均分 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(1),x(7).toInt))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum/x._2.length))
      .toList
      .sortBy(_._2)
      .reverse.foreach(println)
  }
}

object 求学生的年龄分布 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x => (2026 - x(4).take(4).toInt, 1))
      .groupBy(_._1)
      .map(x => (x._1, x._2.size))
      .foreach(println)
  }
}

object 求各月出生人数分布 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).substring(5,7),1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 按照同学们电话号码的大小排序 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(5).toLong)
      .sorted.reverse
      .foreach(println)
  }
}

object 对山西省的同学的成绩排序 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(6),x(7)))
      .filter(_._2.contains("山西"))
      .sortBy(_._3)
      .foreach(println)
  }
}

