package c34
package l6

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.io.{BufferedReader, File, InputStreamReader, PrintStream}
import java.net.{ServerSocket, Socket}
import java.text.SimpleDateFormat
import scala.util.Random
import scala.util.control.Breaks
import scala.util.control.Breaks.breakable

object 基本服务器 {
  def main(args: Array[String]): Unit = {
    val errors = Array("error","info","debug","warn")
    val ss = new ServerSocket(7777)
    val rand = new Random()
    while(true){
      println("server ready")
      val s = ss.accept
      s.setSoTimeout(1)
      val ps = new PrintStream(s.getOutputStream)
      for (i <- 1 to 100){
        val msg = errors(rand.nextInt(4))+":"+i
        ps.println(msg)
        println(msg)
        Thread.sleep(100)
      }
      ps.println("goodbye")
      println("goodbye")
      s.close()
    }
  }
}

object 基本客户端 {
  def main(args: Array[String]): Unit = {
    val s = new Socket("localhost",7777)
    val br = new BufferedReader(new InputStreamReader(s.getInputStream))
    breakable{
      while(true){
        val msg = br.readLine()
        println(msg)
        if( "goodbye".equals(msg)){
          Breaks.break
        }
      }
    }
  }
}

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val localProjectPath = new File("").getAbsolutePath()
}

object 基本SparkStreaming extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[2]","app")
    sc.setLogLevel("ERROR")
    val ssc = new StreamingContext(sc, Seconds(3))
    val lines = ssc.socketTextStream("localhost",7777, StorageLevel.MEMORY_ONLY)
    lines.filter(_.contains("error")).print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 基本SparkStructureStreaming extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val ss = SparkSession.builder()
      .master("local[1]")
      .getOrCreate()
    ss.sparkContext.setLogLevel("ERROR")
    val rs = ss.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",7777)
      .load()
    val query = rs.toDF()
      .filter(_.toString().contains("error"))
    val ws = query.writeStream
      .outputMode("append")
      .format("console")
      .trigger(Trigger.ProcessingTime("1 seconds"))
      .start()
    ws.awaitTermination()
  }
}

class GenRandomStudents {
  def readSrcFile(filename:String) = {
    scala.io.Source
      .fromFile("input/students/"+filename, "UTF-8")
      .getLines()
      .filter(_.length > 0)
      .toArray
  }
  val familyNames = readSrcFile("family_names.txt").flatMap(_.trim.split(""))
  val commonWords = readSrcFile("common_words.txt").flatMap(_.trim.split(""))
  val phoneStart = readSrcFile("phone_start.txt")
  val citys = readSrcFile("citys.txt")

  val rand = new Random
  var clazzs = 1.to(100).map("RB" + "%03d".format(_))
  val genders = Array("男", "女")


  def genOneStudent() = {
    val name = familyNames(rand.nextInt(familyNames.length)) + commonWords(rand.nextInt(commonWords.length)) + commonWords(rand.nextInt(commonWords.length))
    val clz = clazzs(rand.nextInt(clazzs.length))
    val no = clz + "%02d".format(rand.nextInt(100))
    val gender = genders(rand.nextInt(genders.length))
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val birthday = sdf.format(sdf.parse("2000-01-01").getTime +  24l * 3600 * 1000 * rand.nextInt(3 * 365))
    val phone = phoneStart(rand.nextInt(phoneStart.length)) + "%08d".format(rand.nextInt(100000000))
    val loc = citys(rand.nextInt(citys.length)) + "%03d".format(rand.nextInt(1000)) + "号"
    val score = rand.nextInt(100)
    Array(name, no, clz, gender, birthday, phone, loc, score).mkString("\t")
  }
}

object TryGenStudents extends GenRandomStudents{
  def main(args: Array[String]): Unit = {
    println(genOneStudent())
  }
}

object 随机生成学生信息 extends GenRandomStudents {
  def main(args: Array[String]): Unit = {
    var ss = new ServerSocket(7777)
    while (true) {
      println("server ready")
      val s = ss.accept
      s.setSoTimeout(1)
      val ps = new PrintStream(s.getOutputStream)
      breakable {
        for (i <- 0 until 10) {
          val msg = genOneStudent
          ps.println(msg)
          println(msg)
          Thread.sleep(500)
        }
      }
      s.close()
    }
  }
}

object 从服务器获取学生数据 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[2]","app")
    sc.setLogLevel("ERROR")
    val ssc = new StreamingContext(sc, Seconds(3))
    val lines = ssc.socketTextStream("localhost",7777, StorageLevel.MEMORY_ONLY)
    lines.print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

class LoadSparkStreaming{
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val localProjectPath = new File("").getAbsolutePath()
  val sc = new SparkContext("local[2]","app")
  sc.setLogLevel("ERROR")
  val ssc = new StreamingContext(sc, Seconds(3))
  val lines = ssc.socketTextStream("localhost",7777, StorageLevel.MEMORY_ONLY)
}

object 统计男生女生总人数 extends LoadSparkStreaming {
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(3), 1))
      .groupByKey()
      .mapValues(_.sum)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 名字2和3 extends LoadSparkStreaming {
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .filter(x=>x(0).length==2 || x(0).length==3)
      .map(x=>(x(0).length, 1))
      .groupByKey()
      .mapValues(_.sum)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 出生年月日最大值 extends LoadSparkStreaming {
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>x(4).replaceAll("-","").toInt)
      .reduce((x,y)=>if(x>y) x else y)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求80分以下的最大值的同学姓名和成绩 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .filter(x=>x(7).toInt < 80)
      .map(x=>(x(7), x(0)))
      .reduce((x,y)=>if(x._1>y._1) x else y)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求学生中出现的所有姓氏 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(0).substring(0,1) , 1))
      .groupByKey()
      .map(_._1)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}


object 电话前三位 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(5).substring(0,3) , 1))
      .groupByKey()
      .map(_._1)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 平均年龄 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(2024.0 - x(4).substring(0,4).toDouble , 1))
      .reduce((x,y)=>(x._1+y._1, x._2+y._2))
      .map(x=>x._1/x._2)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 平均学号 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(1).substring(2).toDouble , 1))
      .reduce((x,y)=>(x._1+y._1, x._2+y._2))
      .map(x=>x._1/x._2)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

