package c56
package l9

import org.ansj.splitWord.analysis.BaseAnalysis
import org.apache.spark.ml.classification.{LogisticRegression, NaiveBayes}
import org.apache.spark.ml.clustering.LDA
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, RegressionEvaluator}
import org.apache.spark.ml.feature.{CountVectorizer, HashingTF}
import org.apache.spark.ml.regression.{DecisionTreeRegressor, LinearRegression}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Encoders, Row, SparkSession}

import java.io.File
import scala.util.matching.Regex

class LoadHadoop {
  val hadoop_home = "C:\\hadoop\\hadoop-3.2.2"
  System.setProperty("hadoop.home.dir", hadoop_home)
  System.load(hadoop_home + "/bin/hadoop.dll")
  val localProjectPath = new File("").getAbsolutePath()
  val ss = SparkSession.builder()
    .appName("app")
    .master("local[*]")
    .getOrCreate()
  ss.sparkContext.setLogLevel("ERROR")
  val data = ss.read.json("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
  data.createTempView("qa")
  data.show()
}

object 热点问题sql extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val sql1 ="""
    select qid,
           first(title) as title,
           count(*) as num
    from qa
    group by qid
    order by num desc
    limit 10
    """
    ss.sql(sql1).show()
  }
}
import org.apache.spark.sql.functions._
object 热点问题算子 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    data.groupBy("qid")
      .agg(first("title").as("title"),
           count("answer_id").as("answer_count"))
      .orderBy(desc("answer_count"))
      .limit(10)
      .show()
  }
}

object 热点标签sql extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val sql1 ="""
    select t.tags,
           count(t.tags) as tags_num
    from (
        select explode(split(answerer_tags, " ")) as tags
        from qa
    ) t
    group by t.tags
    order by tags_num desc
    """
    ss.sql(sql1).show()
  }
}

object 高赞用户sql extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val sql1 ="""
    select answerer_tags,
           sum(star) as star_sum
    from qa
    group by answerer_tags
    order by star_sum desc
    """
    ss.sql(sql1).show()
  }
}

object 高频词 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    import scala.collection.JavaConverters._
    data.select("content")
      .flatMap(row=>BaseAnalysis.parse(row.getString(0)).getTerms.asScala
        .filter(_.getNatureStr.startsWith("n"))
        .map(_.getName)toList)(Encoders.STRING)
      .filter(_.trim.length>1)
      .toDF("words")
      .createTempView("allwords")
    ss.sql("select * from allwords").show()
    val sql1 ="""
    select words,
           count(words) as words_count
    from allwords
    group by words
    order by words_count desc
    """
    ss.sql(sql1).show()
  }
}
import scala.collection.JavaConverters._
object 情感分析 extends LoadHadoop {
  def main(args: Array[String]): Unit = {
    val dataScheme = StructType(Array(StructField("word",StringType),StructField("score",IntegerType)))
    val posDict = ss.sparkContext.textFile("input\\dict\\正面词.dict").filter(_.length>0).map(x=>Row(x,1))
    val negDict = ss.sparkContext.textFile("input\\dict\\负面词.dict").filter(_.length>0).map(x=>Row(x,-1))
    val fullDict = ss.createDataFrame(posDict, dataScheme).union( ss.createDataFrame(negDict, dataScheme))
    fullDict.createTempView("dict")
    fullDict.show(10000)

    data.select("qid", "title","content")
      .flatMap(row=>BaseAnalysis.parse(row.getString(2))
        .getTerms().asScala
        .map(_.getName)
        .filter(_.length>1)
        .map(x=>(row.getLong(0).toString, row.getString(1), x))
      )(Encoders.tuple(Encoders.STRING, Encoders.STRING, Encoders.STRING))
      .toDF("qid","title","word")
      .createTempView("toks")
    ss.sql("select * from toks").show()

    val sql1 ="""
    select toks.qid as qid,
           first(toks.title) as title,
           sum(dict.score) as score,
           collect_list(dict.score) as score_list
    from toks
    join dict
    on dict.word=toks.word
    group by qid
    order by score desc
    """
    ss.sql(sql1).show()
  }
}

class ContentTok extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = { // 输入格式
    new StructType().add("content",StringType)
  }
  override def bufferSchema: StructType = { // 输出格式
    new StructType().add("content_tok",StringType)
  }
  override def dataType: DataType = StringType
  override def deterministic: Boolean = true
  override def initialize(buffer: MutableAggregationBuffer): Unit = { // 缓冲区初始化
    buffer(0)=""
  }
  val pattern = new Regex("[\\u4E00-\\u9FA5]+")
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = { // 更新代码
    val words = BaseAnalysis.parse(input.getString(0)).getTerms() // 处理分词
      .asScala
      .map(_.getName)
      .mkString(",") //以逗号隔开
    buffer(0) =buffer(0)+","+words
  }
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0)=buffer1.getString(0)+","+buffer2.getString(0)
  }
  override def evaluate(buffer: Row): Any = { // 最终返回的，去除多个逗号
    buffer.getString(0).split(",")
      .filter(_.length>1) // 过滤掉长度短的
      .filter(pattern.findFirstIn(_).size>0) // 过滤掉汉字以外的
      .mkString(",")
  }
}

object 自定义转换函数 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    // 注册agg函数
    ss.udf.register("content_tok",new ContentTok)
    val sql1 = """
    select qid,
           first(title) as title,
           concat_ws(';;', collect_list(content)) as content_all,
           content_tok(content) as content_tok
    from qa
    group by qid
"""
    ss.sql(sql1).show(false)

  }
}

object 添加分类回归值 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val dictScheme = StructType(Array(StructField("word",StringType),StructField("score",IntegerType)))
    val posDict = ss.sparkContext.textFile("input\\dict\\正面词.dict").filter(_.length>0).map(x=>Row(x,1))
    val negDict = ss.sparkContext.textFile("input\\dict\\负面词.dict").filter(_.length>0).map(x=>Row(x,-1))
    val fullDictDF = ss.createDataFrame(posDict,dictScheme).union(ss.createDataFrame(negDict, dictScheme))
    fullDictDF.createTempView("dict")
    ss.udf.register("content_tok",new ContentTok)
    val sql1 = """
        select qid,
               first(title) as title,
               concat_ws(';;', collect_list(content)) as content_all,
               content_tok(content) as content_tok
        from qa
        group by qid
    """
    val title_content = ss.sql(sql1)
    title_content.show()
    title_content.createTempView("title_content")
    val sql2 = "select explode(split(content_tok, ','))  as word ,qid from title_content"
    val word_qid = ss.sql(sql2)
    word_qid.show()
    word_qid.createTempView("word_qid")
    val sql3 ="""
      select t3.qid,
             t3.title,
             t3.content_all,
             t3.content_tok,
             t4.score as reg_score,
             if(t4.score > 0, 1, 0) as cls_score
      from title_content as t3 join
      ( select t2.qid as t2qid,
               sum(t1.score) as score
        from dict as t1
        join word_qid as t2
        on t1.word=t2.word
        group by t2qid
      ) as t4
      on t3.qid=t4.t2qid
      order by t3.qid desc
    """
    val qid_score = ss.sql(sql3)
    qid_score.show()
    qid_score.write.mode("overwrite").json("output/nlp_reg_cls")

  }
}

object 特征化 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val reg_cls = ss.read.json("output\\nlp_reg_cls")
    reg_cls.createTempView("reg_cls")
    val sql1 ="""
      select qid,
             title,
             cls_score,
             reg_score,
             split(content_all,';;') as content_arr,
             split(content_tok,',') as tok_arr
      from reg_cls
    """
    val tf_pre=  ss.sql(sql1)
    tf_pre.show()
    // 使用词频构建特征
    val tf = new HashingTF()
      .setNumFeatures(100)
      .setInputCol("tok_arr")
      .setOutputCol("features")
    val features_label = tf.transform(tf_pre)
    features_label.show(false)
    // feature不能以json的形式保存，读取后会因tinyint的格式出问题
    features_label.coalesce(1).write.mode("overwrite").format("parquet")
      .save("output\\nlp_features")
  }
}

object 贝叶斯分类 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val features_label = ss.read.format("parquet")
      .load("output\\nlp_features")
    features_label.show()
    val Array(train_data, test_data) = features_label.randomSplit(Array(0.7, 0.3))
    val model_nb = new NaiveBayes()
      .setFeaturesCol("features")
      .setLabelCol("cls_score")
      .fit(train_data)
    val train_result_nb = model_nb.transform(train_data)
    val test_result_nb = model_nb.transform(test_data)
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("cls_score")
      .setRawPredictionCol("prediction")
    println("nb训练集准确率="+evaluator.evaluate(train_result_nb))
    println("nb测试集准确率="+evaluator.evaluate(test_result_nb))
  }
}

object 逻辑回归分类 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val features_label = ss.read.format("parquet")
      .load("output\\nlp_features")
    features_label.show()
    val Array(train_data, test_data) = features_label.randomSplit(Array(0.7, 0.3))
    val model_lr = new LogisticRegression()
      .setFeaturesCol("features")
      .setLabelCol("cls_score")
      .fit(train_data)
    val train_result_nb = model_lr.transform(train_data)
    val test_result_nb = model_lr.transform(test_data)
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("cls_score")
      .setRawPredictionCol("prediction")
    println("lr训练集准确率="+evaluator.evaluate(train_result_nb))
    println("lr测试集准确率="+evaluator.evaluate(test_result_nb))
  }
}
object 线性回归 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val features_label = ss.read.format("parquet")
      .load("output\\nlp_features")
    features_label.show()
    val Array(train_data, test_data) = features_label.randomSplit(Array(0.7, 0.3))
    val model_linear = new LinearRegression()
      .setFeaturesCol("features")
      .setLabelCol("reg_score")
      .setRegParam(0.0)
      .setMaxIter(100)
      .setTol(1E-3)
      .fit(train_data)
    val train_result_nb = model_linear.transform(train_data)
    val test_result_nb = model_linear.transform(test_data)
    val evaluator = new RegressionEvaluator("r2")
      .setMetricName("r2")
      .setLabelCol("reg_score")
      .setPredictionCol("prediction")
    println("线性模型训练集r2="+(1-evaluator.evaluate(train_result_nb)))
    println("线性模型测试集r2="+(1-evaluator.evaluate(test_result_nb)))
  }
}

object 决策树回归 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val features_label = ss.read.format("parquet")
      .load("output\\nlp_features")
    features_label.show()
    val Array(train_data, test_data) = features_label.randomSplit(Array(0.7, 0.3))
    val model = new DecisionTreeRegressor()
      .setFeaturesCol("features")
      .setLabelCol("reg_score")
      .setMaxDepth(5)
      .fit(train_data)
    val train_result_nb = model.transform(train_data)
    val test_result_nb = model.transform(test_data)
    val evaluator = new RegressionEvaluator("r2")
      .setMetricName("r2")
      .setLabelCol("reg_score")
      .setPredictionCol("prediction")
    println("决策树训练集r2="+(1-evaluator.evaluate(train_result_nb)))
    println("决策树测试集r2="+(1-evaluator.evaluate(test_result_nb)))
  }
}


object 主题词挖掘 extends LoadHadoop{
  def main(args: Array[String]): Unit = {
    val features_label = ss.read.format("parquet")
      .load("output\\nlp_features")
    features_label.show()
    // 对tok_arr向量化
    val model_vec = new CountVectorizer()
      .setInputCol("tok_arr")
      .setOutputCol("tok_vec")
      .setVocabSize(3000)
    val fit_vec = model_vec.fit(features_label)
    println(fit_vec.vocabulary)
    val trans_vec = fit_vec.transform(features_label)
    trans_vec.show()
    val model_lda = new LDA()
      .setFeaturesCol("tok_vec")
      .setK(20)
      .setMaxIter(20)
      .fit(trans_vec)
    val result_lda = model_lda.transform(trans_vec)
    result_lda.select("title","topicDistribution").show(false)
    // 显示所有话题
    val vec_2_str = udf { (termIndices: Seq[Int]) => termIndices.map(idx => fit_vec.vocabulary(idx)) }
    val topics = model_lda.describeTopics(maxTermsPerTopic = 5)
      .withColumn("terms", vec_2_str(col("termIndices")))
    topics.show()
  }
}




