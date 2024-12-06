package c34
package l8

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.recommendation.{ALS, ALSModel}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.col

import java.io.File

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val localProjectPath = new File("").getAbsolutePath()
}

class LoadData extends LoadHadoop{
  val ss = SparkSession.builder()
    .appName("app")
    .master("local[*]")
    .getOrCreate()
  ss.sparkContext.setLogLevel("ERROR")
  val users = ss.read
    .schema("userid INT, gender STRING, age INT, occupation STRING, zipcode STRING")
    .option("header","false")
    .option("delimiter","::")
    .csv("C:\\share\\data\\ml-1m\\users.dat")
  users.createOrReplaceTempView("users")
  users.printSchema()
  users.show()

  val movies = ss.read
    .schema("movieid INT, title STRING, genres STRING")
    .option("header","false")
    .option("delimiter","::")
    .csv("C:\\share\\data\\ml-1m\\movies.dat")
  movies.createOrReplaceTempView("movies")
  movies.printSchema()
  movies.show()


  val ratings = ss.read
    .schema("userid INT, movieid INT, rating INT, timestamp LONG")
    .option("header","false")
    .option("delimiter","::")
    .csv("C:\\share\\data\\ml-1m\\ratings.dat")
  ratings.createOrReplaceTempView("ratings")
  ratings.printSchema()
  ratings.show()

  val Array(training, test) = ratings.randomSplit(Array(0.8, 0.2), 123)
}

object ALS算法 extends LoadData{
  def main(args: Array[String]): Unit = {
    ss.sparkContext.setCheckpointDir(".checkpoint")
    val als = new ALS()
      .setSeed(1l)
      .setUserCol("userid")
      .setItemCol("movieid")
      .setRatingCol("rating")
      .setRegParam(0.01)
      .setMaxIter(300)
      .setCheckpointInterval(2)
    val model = als.fit(training)
    model.save("output/modelals")
    model.setColdStartStrategy("drop")
    val result = model.transform(test)
    result.show(100, false)
    val eva = new RegressionEvaluator()
      .setMetricName("r2")
      .setLabelCol("rating")
      .setPredictionCol("prediction")
    val r2 = 1-eva.evaluate(result)
    println("r2="+r2)

  }
}

object 参数观察 extends LoadData{
  def main(args: Array[String]): Unit = {
    ss.read.parquet("output/modelals/userFactors")
      .show(false)

    ss.read.parquet("output/modelals/itemFactors")
      .show(false)
  }
}

object 加载模型 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = ALSModel.load("output/modelals")
    model.setColdStartStrategy("drop")
    val result = model.transform(test)
    result.show(100, false)
    val eva = new RegressionEvaluator()
      .setMetricName("r2")
      .setLabelCol("rating")
      .setPredictionCol("prediction")
    val r2 = 1-eva.evaluate(result)
    println("r2="+r2)
  }
}

object 模型运用 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = ALSModel.load("output/modelals")
    model.setColdStartStrategy("drop")
    // 随机选一些人预测他们的兴趣
    val users = ss.sql("select distinct userid from users order by rand()")
    val userRecs = model.recommendForUserSubset(users, 10)
    userRecs.show(100, false)
    userRecs.printSchema()
    userRecs.write.mode(SaveMode.Overwrite).parquet("output/movielen/userRecs")
    // 随机选择一些电影预测哪些人可能会喜欢看
    val movies = ss.sql("select distinct movieid from movies order by rand()")
    val movieRecs = model.recommendForItemSubset(movies, 10)
    movieRecs.show(100, false)
    movieRecs.printSchema()
    movieRecs.write.mode(SaveMode.Overwrite).parquet("output/movielen/movieRecs")

    movieRecs.createTempView("movierecs")
    val sql1 = """
      select m.title,
             r.recommendations
      from movierecs r
      left join movies m
      on r.movieid = m.movieid
    """
    val movieRecs2 = ss.sql(sql1)
    movieRecs2.createTempView("movierecs2")
    movieRecs2.show(10,false)

    val sql2 = """
      select r.userid,
             r.rating,
             m.title
      from ratings r
      left join movies m
      on r.movieid = m.movieid
    """
    val ratings2 = ss.sql(sql2)
    ratings2.createTempView("ratings2")
    ratings2.show()

    userRecs.createTempView("userrecs")
    val sql3 = """
      select t1.userid,
             t1.rec.movieid as movieid,
             t2.title,
             t1.rec.rating as moviescore
      from
          (select userid,
                  explode(recommendations) as rec
          from userrecs) t1
      left join movies t2
      on t1.rec.movieid = t2.movieid
    """
    val userRecs2 = ss.sql(sql3)
    userRecs2.createTempView("userrecs2")
    userRecs2.printSchema()
    userRecs2.show()

    val sql4 = """
      select t1.userid,
             t1.like,
             t2.rec
      from
      (
          select userid,
                 collect_list(title) as like
          from ratings2
          where rating=5
          group by userid
      ) t1
      right join
      (
          select userid,
                 collect_list(title) as rec
          from userrecs2
          group by userid
      ) t2
      on t1.userid = t2.userid
    """
    val userRatingAndRecs = ss.sql(sql4)
    userRatingAndRecs.createTempView("urars")
    userRatingAndRecs.show(100, false)
  }
}

