package c56
package l3

import org.apache.hadoop.hbase.client.{ColumnFamilyDescriptorBuilder, ConnectionFactory, Put, Result, TableDescriptor, TableDescriptorBuilder}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.{TableInputFormat, TableOutputFormat}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, HConstants, TableName}
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.SparkContext
import org.apache.spark.rdd.JdbcRDD

import java.io.File
import java.sql.DriverManager

class LoadSpark{
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")

  val sc = new SparkContext("local", "app", System.getenv("SPARK_HOME"))
  sc.setLogLevel("ERROR")
  val localPath = new File("").getAbsolutePath
}

object 写Mysql extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8"
    val userName = "root"
    val password = "123456"
    val rdd = sc.makeRDD(List(
      ("张三", "RB17105", "RB171","男"),
      ("李四", "RB17106", "RB171","女"),
    ))
    rdd.foreach{
      case (name,no,cls, gender) =>{
        Class.forName(driver)
        val conn = DriverManager.getConnection(url,userName,password)
        val sql = "insert into students(name,no,cls, gender) values(?,?,?,?)"
        val statement = conn.prepareStatement(sql)
        statement.setString(1,name)
        statement.setString(2,no)
        statement.setString(3,cls)
        statement.setString(4,gender)
        statement.executeUpdate()
        statement.close()
        conn.close()
      }
    }
  }
}

object 读Mysql extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8"
    val userName = "root"
    val password = "123456"
    val rdd = new JdbcRDD(sc,
      () => {
        Class.forName(driver)
        DriverManager.getConnection(url, userName, password)
      },
      "select * from students where ? <= id and id <=?;",
      1,
      100,
      1,
      r => (r.getString("name"), r.getString("no"),r.getString("cls"),r.getString("gender"))
    )
    println(rdd.count())
    rdd.foreach(println)
  }
}

object 写HBase extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val hbaseConf = HBaseConfiguration.create(sc.hadoopConfiguration)
    val zks = "192.168.17.150:2181,192.168.17.151:2181,192.168.17.152:2181"
    hbaseConf.set(HConstants.ZOOKEEPER_QUORUM, zks)
    hbaseConf.set(TableOutputFormat.OUTPUT_TABLE, "spark")
    val job = Job.getInstance(hbaseConf)
    job.setOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setOutputValueClass(classOf[Result])
    job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])
    val conn = ConnectionFactory.createConnection(hbaseConf)
    val tableName = TableName.valueOf("spark")
    val admin = conn.getAdmin
    if(!admin.tableExists(tableName)){
      val builder = TableDescriptorBuilder.newBuilder(tableName)
      val familyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("data".getBytes).build
      builder.setColumnFamily(familyDescriptor)
      val tableDescriptor: TableDescriptor = builder.build
      admin.createTable(tableDescriptor)
      import scala.collection.JavaConverters._
      println(admin.listTableNames.toList)
    }
    val rdd = sc.makeRDD(List(
      ("张三", "RB17105", "RB171","男"),
      ("李四", "RB17106", "RB171","女"),
    ))

    val hbaseRDD = rdd.map(value => {
      val put = new Put(Bytes.toBytes(value._2))
      put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("name"), Bytes.toBytes(value._1))
      put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("cls"), Bytes.toBytes(value._3))
      put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("gender"), Bytes.toBytes(value._4))
      (new ImmutableBytesWritable(Bytes.toBytes(value._1)), put)
    })
    hbaseRDD.saveAsNewAPIHadoopDataset(job.getConfiguration)
    admin.flush(tableName)
    sc.stop()
  }
}

object 读HBase extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val hbaseConf = HBaseConfiguration.create(sc.hadoopConfiguration)
    val zks = "192.168.17.150:2181,192.168.17.151:2181,192.168.17.152:2181"
    hbaseConf.set(HConstants.ZOOKEEPER_QUORUM, zks)
    hbaseConf.set(TableInputFormat.INPUT_TABLE, "spark")
    val rdd = sc.newAPIHadoopRDD(
      hbaseConf,
      classOf[TableInputFormat],
      classOf[ImmutableBytesWritable],
      classOf[Result])
    println("count", rdd.count())
    rdd.foreach {
      case (_, res) =>
        for (cell <- res.rawCells()){
          print("RowKey: " + new String(CellUtil.cloneRow(cell)) + ", ");
          print("时间戳: " + cell.getTimestamp() + ", ");
          print("列名: " + new String(CellUtil.cloneQualifier(cell)) + ", ");
          println("值: " + new String(CellUtil.cloneValue(cell)));
        }
    }
    sc.stop()
  }
}

object 数据血统 extends LoadSpark{
  def main(args: Array[String]): Unit = {
    val str = sc.textFile(localPath+"/input/students.data")
      .map(_.split("\t"))
      .filter(_.length == 8)
      .map(x=>(x(3),1))
      .groupByKey()
      .map(x=>(x._1, x._2.size))
      .toDebugString
    println(str)
  }
}

object checkpoint测试 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    //设置检查点目录，可以是HDFS等文件系统
    sc.setCheckpointDir(".checkpoint")
    val rdd1 = sc.makeRDD(Array("Spark"))
    val rdd2_source = rdd1.map(_+":"+System.currentTimeMillis())
    val rdd2_cache = rdd1.map(_+":"+System.currentTimeMillis())
    val rdd2_check = rdd1.map(_+":"+System.currentTimeMillis())
    println("-----------没有设置 Checkpoint-----------")
    for( i <- 1 to 10){
      rdd2_source.foreach(println)
    }
    println("-----------设置 Cache 后-----------")
    rdd2_cache.cache()
    for( i <- 1 to 10){
      rdd2_cache.foreach(println)
    }
    println("-----------设置 Checkpoint 后-----------")
    rdd2_check.checkpoint()
    for( i <- 1 to 10){
      rdd2_check.foreach(println)
    }
    sc.stop()
  }
}

object 无累加器 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val data = sc.textFile("input/hadoop-zzti-namenode-zzti.log",6)
    var errors = 0
    val result = data.map(line => {
      if(line.contains("ERROR")){
        errors += 1
      }
      (line,1)
    })
    println("总行数", result.count())
    println("ERROR数量",errors)
    sc.stop()
  }
}

object 有累加器 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val data = sc.textFile("input/hadoop-zzti-namenode-zzti.log",6)
    val errors = sc.longAccumulator("acc")
    val result = data.map(line => {
      if(line.contains("ERROR")){
        errors.add(1)
      }
      (line,1)
    })
    println(result.count())
    println(errors.value)
    sc.stop()
  }
}

object 无广播变量 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val list = List((1,3),(2,4),(3,6),(4,6),(5,6),(6,6))
    val rdd1 = sc.makeRDD(List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f")))
    val rdd2 = sc.makeRDD(list)
    val result = rdd1.join(rdd2)
    result.foreach(println)
    sc.stop()
  }
}

object 有广播变量 extends LoadSpark {
  def main(args: Array[String]): Unit = {
    val list = List((1,3),(2,4),(3,6),(4,6),(5,6),(6,6))
    val rdd = sc.makeRDD(List((1,"a"),(2,"b"),(3,"c"),(4,"d"),(5,"e"),(6,"f")))
    // 广播变量
    val broad = sc.broadcast(list)
    val result = rdd.map{
      case (key, value) => {
        var e:Any = null
        for (ele <- broad.value){
          if(ele._1 == key){
            e = ele._2
          }
        }
        (key,(value,e))
      }
    }
    result.foreach(println)
    sc.stop()
  }
}
























