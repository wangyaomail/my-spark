package c34
package l6

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

object 年龄分布 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(2024 - x(4).substring(0,4).toInt , 1))
      .groupByKey()
      .mapValues(_.sum)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}


object 各省人数分布 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(6).substring(0,3), 1))
      .groupByKey()
      .mapValues(_.sum)
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 按照电话号码排序 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(5).toLong, x(0)))
//      .foreachRDD(_.sortBy(x=>x._1).foreach(println))
      .transform(_.sortBy(x=>x._1))
      .print()


    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 按学号排序 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(1).substring(2).toInt, x(0)))
      .transform(_.sortBy(x=>x._1))
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 索引相同生日下姓名链表 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(4).substring(5), x(0)))
      .groupByKey()
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 索引相同班级姓名链表 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(2), x(0)))
      .groupByKey()
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 生日最大的五个同学名字 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(4).substring(5).replaceAll("-","").toInt, x(0)))
      .foreachRDD(_.sortBy(x=>(x._1), false).take(5).foreach(println))

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 求80分以下成绩最高的3个同学姓名和成绩 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .filter(_(7).toInt<80)
      .map(x=>(x(0), x(7)))
      .foreachRDD(_.sortBy(x=>(x._2), false).take(2).foreach(println))

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 流拆分 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    val s1 = lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(0), x(3), x(4), x(5)))

    val s2 = s1.transform(x=>{
      x.map(y=>(y._1,y._3))
    })

    s1.print()
    s2.print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 流合并 extends LoadSparkStreaming{
  def main(args: Array[String]): Unit = {
    val s1 = lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>(x(0), 1))

    val s2 = s1.transform(x=>{
      x.map(y=>(y._1, 2))
    })

    s2.join(s1).print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 窗口算子 extends LoadSparkStreaming {
  def main(args: Array[String]): Unit = {
    ssc.checkpoint(".checkpoint")
    lines.window(Seconds(6), Seconds(3))
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 窗口算子countByWindow extends LoadSparkStreaming {
  def main(args: Array[String]): Unit = {
    ssc.checkpoint(".checkpoint")
    lines.countByWindow(Seconds(6), Seconds(3))
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object 窗口算子reduceByWindow extends LoadSparkStreaming {
  def main(args: Array[String]): Unit = {
    ssc.checkpoint(".checkpoint")
    lines.map(_.split("\t"))
      .filter(_.length==8)
      .map(x=>x(0))
      .reduceByWindow(
        _+":"+_,
        Seconds(6),
        Seconds(3)
      )
      .print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}

object structure表结构 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val ss = SparkSession.builder()
      .master("local[2]")
      .getOrCreate()
    ss.sparkContext.setLogLevel("ERROR")
    val rs = ss.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",7777)
      .load()
    val table = rs.selectExpr("split(value, '\t') as stu")
      .withColumn("name", expr("stu[0]"))
      .withColumn("no", expr("stu[1]"))
      .withColumn("clz", expr("stu[2]"))
      .withColumn("gender", expr("stu[3]"))
      .withColumn("birthday", expr("stu[4]"))
      .withColumn("phone", expr("stu[5]"))
      .withColumn("loc", expr("stu[6]"))
      .withColumn("score", expr("stu[7]"))
      .drop("stu")
      .createTempView("students")
    val query = ss.sql("select * from students")
    val ws = query.writeStream
      .outputMode("append")
      .format("console")
      .trigger(Trigger.ProcessingTime("1 seconds"))
      .start()
    ws.awaitTermination()
  }
}


class LoadStreamingTable extends LoadHadoop{
    val ss = SparkSession.builder()
      .master("local[2]")
      .getOrCreate()
    ss.sparkContext.setLogLevel("ERROR")
    val rs = ss.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",7777)
      .load()
    val table = rs.selectExpr("split(value, '\t') as stu")
      .withColumn("name", expr("stu[0]"))
      .withColumn("no", expr("stu[1]"))
      .withColumn("clz", expr("stu[2]"))
      .withColumn("gender", expr("stu[3]"))
      .withColumn("birthday", expr("stu[4]"))
      .withColumn("phone", expr("stu[5]"))
      .withColumn("loc", expr("stu[6]"))
      .withColumn("score", expr("stu[7]"))
      .drop("stu")
      .createTempView("students")
}

object 统计男生信息 extends LoadStreamingTable{
  def main(args: Array[String]): Unit = {
    val query = ss.sql("select * from students where gender='男'")
    val ws = query.writeStream
      .outputMode("append")
      .format("console")
      .trigger(Trigger.ProcessingTime("1 seconds"))
      .start()
    ws.awaitTermination()
  }
}

object 统计男女生人数Append extends LoadStreamingTable{
  def main(args: Array[String]): Unit = {
    val query = ss.sql("select gender,count(gender) from students group by gender")
    val ws = query.writeStream
      .outputMode("append")
      .format("console")
      .trigger(Trigger.ProcessingTime("1 seconds"))
      .start()
    ws.awaitTermination()
  }
}

object 统计男女生人数Complete extends LoadStreamingTable{
  def main(args: Array[String]): Unit = {
    val query = ss.sql("select gender,count(gender) from students group by gender")
    val ws = query.writeStream
      .outputMode("complete")
      .format("console")
      .trigger(Trigger.ProcessingTime("3 seconds"))
      .start()
    ws.awaitTermination()
  }
}

object Kafka基本Producer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.17.150:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    producer.send(new ProducerRecord[String, String]("topic-1", "key1", "value1"))
    producer.send(new ProducerRecord[String, String]("topic-1", "key2", "value2"))
    producer.send(new ProducerRecord[String, String]("topic-1", "key3", "value3"))
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
      "group.id" -> "group-2",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )
    val topics = Set("topic-1")
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


