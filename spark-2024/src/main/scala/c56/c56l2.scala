package c56
package l2

import c56.l2.分区.sc
import org.apache.spark.{HashPartitioner, SparkContext}

import java.io.File


class LoadSpark{
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")

  val sc = new SparkContext("local", "app", System.getenv("SPARK_HOME"))
  sc.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath
}

object PageRank extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val file = sc.textFile("C:\\share\\data\\FB15K-237.2\\Release\\train.txt")
      .map(_.split("\t").toList)
      .filter(_.length==3)
    val idSet = file
      .map(x=>x(2))
      .collect()
      .toSet

//    file.foreach(println)
    var data = file
      .map(x=>(x(0), x(2)))
      .filter(x=>idSet.contains(x._1))
      .groupByKey
      .map(x=>(x._1, (1.0, x._2.toList)))

    for(i<- 1  to 100) {
      data = data.flatMap(x=>(x._2._2.map(y=>(y,(x._1,x._2._1/x._2._2.length)))))
        .groupByKey
        .map(x=>(x._1, x._2.map(y=>y._2).reduce(_+_)))
        .join(data)
        .map(x=>(x._1, (x._2._1, x._2._2._2)))
      println("------第", i, "轮------")
      data.take(3).foreach(println)
    }
    data.map(x=>(x._2._1, x._1))
      .sortByKey(ascending=false)
      .saveAsTextFile("output/result")

  }
}


object 分区 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val list1 = List(1,2,3,4,5)
    sc.makeRDD(list1)
      .foreachPartition(x=>{println("默认");x.foreach(println)})

    sc.makeRDD(list1,3)
      .foreachPartition(x=>{println("3分区");x.foreach(println)})

    sc.makeRDD(list1, 5)
      .foreachPartition(x=>{println("5分区");x.foreach(println)})
    sc.makeRDD(list1, 10)
      .foreachPartition(x=>{println("10分区");x.foreach(println)})



  }
}

object 分区2 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val list1 = List(1,2,3,4,5)
//    sc.makeRDD(list1)
//      .foreachPartition(x=>{println("一开始");x.foreach(println)})
//    sc.makeRDD(list1)
//      .repartition(3)
//      .foreachPartition(x=>{println("repartitions");x.foreach(println)})
//    sc.makeRDD(list1)
//      .repartition(3)
//      .coalesce(2)
//      .foreachPartition(x=>{println("coalesce");x.foreach(println)})

    val kvList = List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f"))
    val data = sc.makeRDD(kvList,3)
    data.foreachPartition(x=>{println("3fenqu");x.foreach(println)})
    data.repartitionAndSortWithinPartitions(new HashPartitioner(3))
      .foreachPartition(x=>{println("repartitionAndSortWithinPartitions");x.foreach(println)})

    data.mapPartitionsWithIndex((x,y)=>y.map(z=>x+":"+z))
      .foreachPartition(x=>{println("mapPartitionsWithIndex");x.foreach(println)})

  }
}


