package C78
package T2

import org.apache.spark.{RangePartitioner, SparkContext}

import java.io.File


object PageRankTest {
  def main(args: Array[String]): Unit = {
    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2";
    System.setProperty("hadoop.home.dir", hadoop_home);
    System.load(hadoop_home + "/bin/hadoop.dll");

    val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))
    val localProjectPath = new File("").getAbsolutePath()
    println("步骤1：统计各个页面的出边，并计算转移矩阵")
    var data = sc.textFile("C:\\share\\data\\FB15K-237.2\\Release\\train.txt")
      .map(_.split("\t"))
      .filter(_.length==3)
      .map(x=>(x(0),x(2)))
      .groupBy(_._1)
      .map(x=>(x._1,(1.0,x._2.map(_._2).toList)))
//      .foreach(println)

    println("步骤2：利用转移矩阵计算停留概率")
    for(i <- 1 to 100){
      println("第几轮：",i)
      data = data.flatMap(x=>(x._2._2.map(y=>(y,x._2._1/x._2._2.length))))
        .groupByKey()
        .map(x=>(x._1,x._2.sum))
        .join(data)
        .map(x=>(x._1,(x._2._1, x._2._2._2)))
    }
    data.sortBy(_._2._1, false)
      .take(100)
      .foreach(println)

//    data.flatMap(x=>(x._2._2.map(y=>(y,x._2._1/x._2._2.length))))
//      .groupByKey()
//      .map(x=>(x._1,x._2.sum))
//      .join(data)
//      .map(x=>(x._1,(x._2._1, x._2._2._2)))
//
//      .foreach(println)
  }
}


object 分区 {
  def main(args: Array[String]): Unit = {

    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2";
    System.setProperty("hadoop.home.dir", hadoop_home);
    System.load(hadoop_home + "/bin/hadoop.dll");

    val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))


    val l1 = List(1,2,3,4,5,6)
    val r1 = sc.makeRDD(l1)
//    r1.foreach(println)

    val r2 = sc.makeRDD(l1,3)
//    r2.foreach(println)
//    r2.foreachPartition(itr=>{
//      println("分区",itr.map(x=>x).toList)
//    })
//    r2.map(_+1)
//      .sortBy(x=>x,false)
//      .foreach(println)
//    r2.mapPartitions(itr=>{
//        println("分区" )
//        itr.map(x=>x).toList
//    })
//      .foreach(println)
//    r2.mapPartitionsWithIndex((i,itr)=>{
//      itr.map(x=>"分区"+i+"-"+x)
//    })
//      .foreach(println)

    val r3 = sc.makeRDD(List(1,2,3,4,5,6))
      .zip(sc.makeRDD(List("a","b","c","d","e","f")))
    r3.partitionBy(new RangePartitioner(2,r3))
        .mapPartitionsWithIndex((i,itr)=>{
          itr.map(x=>"分区"+i+"-"+x)
        })
      .foreach(println)
  }
}

object 分区2 {
  def main(args: Array[String]): Unit = {
    val hadoop_home = "C:\\hadoop\\hadoop-3.2.2";
    System.setProperty("hadoop.home.dir", hadoop_home);
    System.load(hadoop_home + "/bin/hadoop.dll");

    val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))


    val l1 = List(1,2,3,4,5,6)
    val r1 = sc.makeRDD(l1,3)
    r1.coalesce(2)
      .repartition(4)
      .mapPartitionsWithIndex((i,itr)=>{
        itr.map(x=>"分区"+i+"-"+x)
      })
      .foreach(println)
  }
}




