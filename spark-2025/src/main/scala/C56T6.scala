package c56
package t6

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.expr
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.io.{BufferedReader, File, InputStreamReader, PrintStream}
import java.net.{ServerSocket, Socket}
import java.text.SimpleDateFormat
import java.time.Duration
import java.util
import java.util.Properties
import scala.util.Random
import scala.util.control.Breaks
import scala.util.control.Breaks.breakable

object 基础服务器 {
  def main(args: Array[String]): Unit = {
    val errors = Array("warn", "error", "info", "debug")
    val rand = new Random
    var ss = new ServerSocket(7777)
    while (true) {
      println("server ready.")
      val s = ss.accept
      s.setSoTimeout(1)
      val ps = new PrintStream(s.getOutputStream)
      for (i <- 0 until 100) {
        val msg = errors(rand.nextInt(4)) + ":" + i
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

object 基础客户端 {
  def main(args: Array[String]): Unit = {
    val s = new Socket("localhost", 7777)
    val br = new BufferedReader(new InputStreamReader(s.getInputStream))
    breakable {
      while (true) {
        val line = br.readLine.trim
        println(line)
        if ("goodbye" == line) {
          Breaks.break()
        }
      }
    }
    s.close()
  }
}

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2";
  System.setProperty("hadoop.home.dir", hadoop_home);
  System.load(hadoop_home + "/bin/hadoop.dll");
  val localProjectPath = new File("").getAbsolutePath();
}

object 基础客户端Spark extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[2]","app", System.getenv("SPARK_HOME"))
    sc.setLogLevel("ERROR")
    val ssc = new StreamingContext(sc, Seconds(3))
    val lines = ssc.socketTextStream("localhost",7777,StorageLevel.MEMORY_ONLY)
    lines.print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 基本StructuredSparkStreaming extends LoadHadoop {
  def main(args: Array[String]): Unit = {
    val ss = SparkSession.builder
      .master("local[1]")
      .getOrCreate()
    ss.sparkContext.setLogLevel("ERROR")
    val lines = ss.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 7777)
      .load()
    val query = lines
      .toDF()
      .filter(_.toString().contains("error"))
      .writeStream
      .outputMode("append")
      .format("console")
      .trigger(Trigger.ProcessingTime("1 seconds"))
      .start()
    query.awaitTermination()
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

object 测试学生数据 extends GenRandomStudents{
  def main(args: Array[String]): Unit = {
    println(genOneStudent())
    println(genOneStudent())
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
        for (i <- 0 until 20) {
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

object 统计男生和女生的总人数 extends LoadHadoop {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[2]", "myapp", System.getenv("SPARK_HOME"))
    sc.setLogLevel("ERROR")
    val ssc = new StreamingContext(sc, Seconds(3))
    val lines = ssc.socketTextStream("localhost", 7777, StorageLevel.MEMORY_ONLY)
    lines.map(_.split("\t"))
      .map(x => (x(3), 1))
      .groupByKey()
      .mapValues(_.sum)
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}


class StuBaseClass extends LoadHadoop{
  val sc = new SparkContext("local[2]", "myapp", System.getenv("SPARK_HOME"))
  sc.setLogLevel("ERROR")
  val ssc = new StreamingContext(sc, Seconds(3))
  val lines = ssc.socketTextStream("localhost", 7777, StorageLevel.MEMORY_ONLY)
}

object 男女生人数2 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x => (x(3), 1))
      .groupByKey()
      .mapValues(_.sum)
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 统计60分以上和60分以下的人数 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x => (if(x(7).toInt>=60) ">=60" else "<60", 1))
      .groupByKey()
      .mapValues(_.sum)
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求出生年月日最大值 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x => x(4))
      .reduce((x,y)=>if(x>y) x else y)
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求河南的学生的最高分 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x => (x(6),x(7)))
      .filter(_._1.contains("河南"))
      .reduce((x,y)=>if(x._2>y._2) x else y)
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求学生中出现的所有姓氏 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x => (x(0).take(1),1))
      .groupByKey()
      .map(_._1)
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求平均年龄 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>(2025-x(4).substring(0,4).toInt,1))
      .reduce((x,y)=>(x._1+y._1, x._2+y._2))
      .map(x=>(x._1,x._2,x._1/x._2))
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 手机号后四位均值 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>(x(5).takeRight(4).toInt,1))
      .reduce((x,y)=>(x._1+y._1, x._2+y._2))
      .map(x=>(x._1,x._2,x._1/x._2))
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求年龄分布 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>(2025-x(4).substring(0,4).toInt,1))
      .groupByKey()
      .map(x=>(x._1,x._2.sum))
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 按照同学们电话号码的大小排序 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>x(5))
      .foreachRDD(y=>y.sortBy(z=>z, false).foreach(println))
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 按门牌号排序输出学生名字和住址 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>x(6).takeRight(4).take(3))
      .foreachRDD(y=>y.sortBy(z=>z, false).foreach(println))
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 索引出相同生日下同学的姓名链表 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>(x(4).substring(5),x(0)))
      .groupByKey()
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求出生日最大的2个同学的名字 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>(x(4).substring(5),"00-00"))
      .reduce((x,y)=>(
        if(x._1<y._1)
          (y._1,x._2)
        else if(x._2<y._1) (x._1, y._1)
        else (x._1,x._2)
        ))
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求手机号各位数字之和最大的2个同学 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>((x(5).map(_.toInt-'0'.toInt).sum,x(0)),(0,"")))
      .reduce((x,y)=>(
        if(x._1._1<y._1._1)
          (y._1,x._2)
        else if(x._2._1<y._1._1) (x._1, y._1)
        else (x._1,x._2)
        ))
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 流合并 extends StuBaseClass {
  def main(args: Array[String]): Unit = {
    val name_id = lines.map(_.split("\t")).map(x => (x(0), x(1)))
    val name_gender = lines.map(_.split("\t")).map(x => (x(0), x(3)))
    name_id.join(name_gender)
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 原生算子 extends StuBaseClass{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .map(x=>x(5))
      .transform(x=>x.sortBy(z=>z, false))
      .print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

class BaseStructuredSparkStreaming extends LoadHadoop {
    val ss = SparkSession.builder
      .master("local[1]")
      .getOrCreate()
    ss.sparkContext.setLogLevel("ERROR")
    val lines = ss.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 7777)
      .load()
    lines.selectExpr("split(value,'\t') as stu")
      .withColumn("name", expr("stu[0]"))
      .withColumn("no", expr("stu[1]"))
      .withColumn("cls", expr("stu[2]"))
      .withColumn("gender", expr("stu[3]"))
      .withColumn("birthday", expr("stu[4]"))
      .withColumn("phone", expr("stu[5]"))
      .withColumn("loc", expr("stu[6]"))
      .withColumn("score", expr("stu[7]"))
      .drop("stu")
      .createTempView("students")
}

object 统计男生信息 extends BaseStructuredSparkStreaming{
  def main(args: Array[String]): Unit = {
    ss
      .sql("select * from students where gender='男'")
      .writeStream
      .outputMode("append")
      .format("console")
      .trigger(Trigger.ProcessingTime("3 seconds"))
      .start()
      .awaitTermination()
  }
}


object 统计男女生数量 extends BaseStructuredSparkStreaming{
  def main(args: Array[String]): Unit = {
    ss
      .sql("select gender,count(*) from students group by gender")
      .writeStream
      .outputMode("complete")
      .format("console")
      .trigger(Trigger.ProcessingTime("3 seconds"))
      .start()
      .awaitTermination()
  }
}

object Kafka基本Producer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.17.150:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    producer.send(new ProducerRecord[String, String]("topic-2", "key1", "value1"))
    producer.send(new ProducerRecord[String, String]("topic-2", "key2", "value2"))
    producer.send(new ProducerRecord[String, String]("topic-2", "key3", "value3"))
    producer.close()
  }
}

object Kafka基本Consumer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.17.150:9092")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList("topic-1"))
    try {
      while (true) {
        val records = consumer.poll(Duration.ofSeconds(1))
        records.forEach(record => println(s"offset = ${record.offset}, key = ${record.key}, value = ${record.value}"))
      }
    } finally {
      consumer.close()
    }
  }
}

object 基本SparkStreaming连接Kafka extends LoadHadoop {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[2]", "myapp", System.getenv("SPARK_HOME"))
    sc.setLogLevel("ERROR")
    val ssc = new StreamingContext(sc, Seconds(1))
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "zzti:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group-1",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )
    val topics = Set("topic-2")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    stream.foreachRDD(_.foreach(println))
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
