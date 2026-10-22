package c34
package l05

import scala.io.Source

object 统计男生和女生的总人数 {
  def main(args: Array[String]): Unit = {
    Source.fromFile("input/students_10w.data")
      .getLines()
      .map(_.trim.split("\t"))
      .toList
      .map(x=>(x(3),1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.length))
      .foreach(println)
  }
}

class StuBase {
  val data = Source.fromFile("input/students_10w.data")
    .getLines()
    .map(_.trim.split("\t"))
    .toList
}

object 同学们生日在上半年和下半年的数量 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>x(4).substring(5,7).toInt)
      .map(x => (if( x > 6 ) "下半年" else "上半年", 1))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.length))
      .foreach(println)
  }
}

object 求出生年月日最大值 extends StuBase {
  def main(args: Array[String]): Unit = {
    val age = data.map(x=>x(4).replaceAll("-","").toInt)
    println(age.max,age.min,age.sorted.take(1), age.sorted.takeRight(1))
  }
}

object 求手机号最大值 extends StuBase {
  def main(args: Array[String]): Unit = {
    println(data.map(_(5).toLong).max)
  }
}

object 求学生中出现的所有姓氏 extends StuBase {
  def main(args: Array[String]): Unit = {
    println(data.map(_(0).substring(0,1)).distinct)
    println("_______")
    println(data.map(x=>(x(0).substring(0,1),1))
      .groupBy(_._1)
      .map(_._1)
      .mkString("、"))
  }
}

object 手机号后8位的所有不同的取值 extends StuBase {
  def main(args: Array[String]): Unit = {
    println(data.map(_(5).substring(3)).distinct)
  }
}

object 求平均年龄 extends StuBase {
  def main(args: Array[String]): Unit = {
    val age = data.map(2026 - _(4).substring(0,4).toInt)
    println(age.sum / age.length )
  }
}


object 各班平均分 extends StuBase {
  def main(args: Array[String]): Unit = {
    val age = data.map(x=>(x(1), x(7).toInt))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.map(_._2).sum/ x._2.length))
      .foreach(println)
  }
}


object 求学生的年龄分布 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(2026 - _(4).substring(0,4).toInt)
      .map(x=>(x,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.length))
      .foreach(println)
  }
}

object 求学生的月分布 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(_(4).substring(5,7).toInt)
      .map(x=>(x,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.length))
      .foreach(println)
  }
}

object 按照同学们电话号码的大小排序 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(_(5).toLong)
      .sorted
      .foreach(println)
  }
}

object 对山西省的同学的成绩排序 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0), x(6), x(7).toInt))
      .filter(_._2.contains("山西"))
      .sortBy(_._3)
      .foreach(println)
  }
}

object 索引出相同生日下同学的姓名链表 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(4).substring(5)))
      .groupBy(_._2)
      .map(x=>(x._1,x._2.map(_._1).mkString(",")))
      .foreach(println)
  }
}

object 索引广西省90100分的同学姓名 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0),x(6),x(7).toInt))
      .filter(x=>x._2.contains("广西") && x._3>=90)
      .groupBy(_._3)
      .map(x=>(x._1,x._2.map(_._1).mkString(",")))
      .toList
      .sortBy(_._1)
      .foreach(println)
  }
}

object 求出生日最大的5个同学的名字 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(0), x(4)))
      .sortBy(_._2)
      .reverse
      .take(5)
      .foreach(println)
  }
}

object 生日的年月日的数值和是奇数的同学比偶数的同学多几人 extends StuBase {
  def main(args: Array[String]): Unit = {
    val d = data.map(x=>x(4).split("-").toList)
      .map(x=>(x(0).toInt+x(1).toInt+x(2).toInt)%2)
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.length)).toList
    println(d(0),d(1),d(0)._2-d(1)._2)
  }
}

object 韩命古同学在他们班排第几 extends StuBase {
  def main(args: Array[String]): Unit = {
    val d = data.map(x=>(x(0),x(1),x(7)))
      .groupBy(_._2)
      .filter(x=>x._2.filter(y=>y._1=="韩命古").size>0)
      .map(_._2).toList(0)
      .sortBy(_._3)
      .reverse
    d.zip(Range(1,d.size))
      .filter(_._1._1 == "韩命古")
      .foreach(println)
  }
}

object 手机号后N位重复的人 extends StuBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(5).substring(4),1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .filter(_._2>0)
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

class 西瓜 {
    val data = Source.fromFile("input/xigua3.0.csv")
      .getLines()
      .map(_.split(",").toList)
      .drop(1)
}

object 平均密度 extends 西瓜 {
  def main(args: Array[String]): Unit = {
    val midu = data.map(x=>x(7).toDouble).toList
    println(midu.sum/midu.length, midu)
  }
}