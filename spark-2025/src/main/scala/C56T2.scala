package c56
package t2

import org.apache.spark.{HashPartitioner, RangePartitioner, SparkContext}

class SparkBase {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2";
  System.setProperty("hadoop.home.dir", hadoop_home);
  System.load(hadoop_home + "/bin/hadoop.dll");
  val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))

}

object PageRankC56 extends SparkBase {
  def main(args: Array[String]): Unit = {
    println("步骤1：统计各个页面的出边，并计算转移矩阵")
    var data = sc.textFile("C:\\share\\data\\FB15K-237.2\\Release\\train.txt")
      .map(_.split("\t"))
      .filter(_.length==3)
      .map(x=>(x(0), x(2)))
      .groupBy(_._1)
      .map(x=>(x._1, (1.0, x._2.map(_._2).toList)))



    for(i <- 1 to 20 ){
      data = data.flatMap(x=>(x._2._2.map(y=>(y,x._2._1/x._2._2.length))))
        .groupBy(_._1)
        .map(x=>(x._1,x._2.map(y=>y._2).sum))
        .join(data)
        .map(x=>(x._1, (x._2._1, x._2._2._2)))
      println("第"+i+"轮", data.take(3).toList)
    }

    data.sortBy(_._2._1, false)
      .take(100)
      .foreach(println)

  }
}

object 分区1 extends SparkBase{
  def main(args: Array[String]): Unit =   {
    val l1 = List(1,2,3,4,5,6)
    val r1 = sc.makeRDD(l1)
//    println(r1.collect().toList)
    val r2 = sc.makeRDD(l1,3)
//    println(r2.collect().toList)
//    r2.map(_+1)
//      .sortBy(x=>x, false)
//      .foreach(println)
//    r2.foreachPartition(itr=>{
//      println("分区",itr.toList)
//    })
//    r2.mapPartitions(itr=>{
//      println(itr.toList)
//      itr
//    }).collect()
//    r2.mapPartitionsWithIndex((i,itr)=>{
//      itr.map(x=>"分区【"+i+"】："+x)
//    }).foreach(println)
  }
}

object 分区2 extends SparkBase{
  def main(args: Array[String]): Unit = {
    val l1 = List(1,2,3,4,5,6)
    val l2 = List("a","b","c","d","e","f")
    val r1 = sc.makeRDD(l1.zip(l2))
    val r2 = sc.makeRDD(l1).zip(sc.makeRDD(l2))
//    r1.partitionBy(new HashPartitioner(4))
//      .coalesce(2)
//      .repartition(30)
//      .foreachPartition(itr=>{
//        println("分区", itr.toList)
//      })
//    r1.repartitionAndSortWithinPartitions(new HashPartitioner(3))
//      .mapPartitionsWithIndex((i,itr)=>{
//            itr.map(x=>"分区【"+i+"】："+x)
//          }).foreach(println)
    r2.repartition(4)
      .glom()
      .mapPartitionsWithIndex((i,itr) => {
        itr.map(x=>i+"-"+x.toList)
      })
      .foreachPartition(itr => {
        itr.foreach(x => print(x + ","))
        println("")
      })
  }
}


