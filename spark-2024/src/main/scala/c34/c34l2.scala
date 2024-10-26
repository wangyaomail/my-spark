package c34
package l2

import c34.l2.分区1.sc
import org.apache.spark.{HashPartitioner, SparkContext}

import java.io.File

class LoadSpark{
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
  sc.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath()
}

object PageRank extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val file = sc.textFile("C:\\share\\data\\FB15K-237.2\\Release\\train.txt")
      .map(_.split("\t").toList)
      .filter(_.length==3)

    var data = file.map(x=>(x(0), x(2)))
      .groupByKey()
      .map(x=>(x._1, (1.0, x._2.toList)))

    for(i <- 1 to 100){
      data = data.flatMap(x=>(x._2._2.map(y=>(y,(x._2._1/x._2._2.length, x._1)))))
        .groupByKey()
        .map(x=>(x._1, x._2.map(_._1).reduce(_+_)))
        .join(data)
        .map(x=>(x._1,(x._2._1,x._2._2._2)))
      println("-------第",i,"轮-------")
      data.take(3).foreach(println)
    }

    data.map(x=>(x._2._1, x._1))
      .sortByKey(ascending = false)
      .saveAsTextFile("output/c34result")



  }
}

object 分区1 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,4,5,6)
    sc.makeRDD(list, 3)
      .foreachPartition(x=>{println("分区"); x.foreach(println)})
    println("mapPartitions")
    sc.makeRDD(list, 3)
      .mapPartitions(x=>x.map(_+1))
      .foreachPartition(x=>{println("分区"); x.foreach(println)})
    println("mapPartitionsWithIndex")
    val l2 = List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f"))
    sc.makeRDD(l2, 3)
      .mapPartitionsWithIndex((x,y)=>y.map(z=>x+":"+z))
      .foreachPartition(x=>{println("分区"); x.foreach(println)})
    println("partitionBy")
    sc.makeRDD(l2, 3)
      .partitionBy(new HashPartitioner(3))
      .mapPartitionsWithIndex((x,y)=>y.map(z=>x+":"+z))
      .foreachPartition(x=>{println("分区"); x.foreach(println)})
  }
}

object 分区2 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val l2 = List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f"))
    var fq1 = sc.makeRDD(l2, 3)
    fq1.foreachPartition(x=>{println("分区"); x.foreach(println)})
    println("coalesce")
    fq1.coalesce(2).foreachPartition(x=>{println("分区"); x.foreach(println)})
    println("repartition")
    fq1.repartition(4).foreachPartition(x=>{println("分区"); x.foreach(println)})
    println("==")
    sc.makeRDD(l2, 6).foreachPartition(x=>{println("分区"); x.foreach(println)})
    println("over")
    sc.makeRDD(l2, 60).foreachPartition(x=>{println("分区"); x.foreach(println)})

  }
}





