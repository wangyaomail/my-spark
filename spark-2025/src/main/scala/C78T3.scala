package c78
package t3

import org.apache.hadoop.hbase.client.{ColumnFamilyDescriptorBuilder, ConnectionFactory, Put, Result, TableDescriptor, TableDescriptorBuilder}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, HConstants, TableName}
import org.apache.hadoop.hbase.mapreduce.{TableInputFormat, TableOutputFormat}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.SparkContext
import org.apache.spark.rdd.JdbcRDD

import java.io.File
import java.sql.DriverManager

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val localProjectPath = new File("").getAbsolutePath()
  val sc = new SparkContext("local", "myapp", System.getenv("SPARK_HOME"))

}

object 读Mysql extends LoadHadoop{
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
      13,
      1,
      r => (r.getString("name"),
        r.getString("no"),
        r.getString("cls"),
        r.getString("gender"))
    )
    println(rdd.count())
    rdd.foreach(println)
  }
}

object 写Mysql extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://192.168.17.150:3306/spark?useUnicode=true&characterEncoding=UTF-8"
    val userName = "root"
    val password = "123456"
    val rdd = sc.makeRDD(List(
      ("张三", "RB17105", "RB171","男"),
      ("李四", "RB17106", "RB171","女"),
      ("张三", "RB17105", "RB171","男"),
      ("李四", "RB17106", "RB171","女"),
      ("张三", "RB17105", "RB171","男"),
      ("李四", "RB17106", "RB171","女"),
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

object 写HBase extends LoadHadoop{
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

object 读HBase extends LoadHadoop {
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
