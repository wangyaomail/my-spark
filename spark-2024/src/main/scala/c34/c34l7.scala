package c34
package l7

import org.apache.spark.ml.classification.{DecisionTreeClassifier, LinearSVC, LogisticRegression, NaiveBayes}
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, RegressionEvaluator}
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.regression.{DecisionTreeRegressor, LinearRegression}
import org.apache.spark.sql.SparkSession
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
  val data = ss.read
    .option("header","true")
    .csv("input/xigua3.0.csv")
  data.printSchema()
  data.show()
  val cols = Array("色泽","根蒂","敲声","纹理","脐部","触感","好瓜")
  val si = new StringIndexer()
    .setInputCols(cols)
    .setOutputCols(cols.map(_+"2"))
  val data2 = si.fit(data).transform(data)
    .withColumn("密度2", col("密度").cast("double"))
    .withColumn("含糖率2", col("含糖率").cast("double"))
  data2.printSchema()
  data2.show()
  val data3 = new VectorAssembler()
    .setInputCols(Array("密度2","含糖率2","色泽2","根蒂2","敲声2","纹理2","脐部2","触感2"))
    .setOutputCol("特征")
    .transform(data2)
  data3.printSchema()
  data3.show(false)
}

object 线性分类模型 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = new LogisticRegression()
      .setFeaturesCol("特征")
      .setLabelCol("好瓜2")
      .fit(data3)
    val data_result = model.transform(data3)
    data_result.show()
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("好瓜2")
      .setRawPredictionCol("rawPrediction")
    println("逻辑回归分类AUC", evaluator.evaluate(data_result))
  }
}

object 线性回归模型 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = new LinearRegression()
      .setFeaturesCol("特征")
      .setLabelCol("含糖率2")
      .fit(data3)
    val data_result = model.transform(data3)
    data_result.show()
    val evaluator = new RegressionEvaluator("r2")
      .setLabelCol("含糖率2")
      .setPredictionCol("prediction")
    println("线性回归r^2", 1- evaluator.evaluate(data_result))
  }
}

object 决策树分类模型 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = new DecisionTreeClassifier()
      .setFeaturesCol("特征")
      .setLabelCol("好瓜2")
      .setMaxDepth(3)
      .fit(data3)
    val data_result = model.transform(data3)
    data_result.show()
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("好瓜2")
      .setRawPredictionCol("rawPrediction")
    println("决策树分类AUC", evaluator.evaluate(data_result))
  }
}

object 决策树回归模型 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = new DecisionTreeRegressor()
      .setFeaturesCol("特征")
      .setLabelCol("含糖率2")
      .setMaxDepth(3)
      .fit(data3)
    val data_result = model.transform(data3)
    data_result.show()
    val evaluator = new RegressionEvaluator("r2")
      .setLabelCol("含糖率2")
      .setPredictionCol("prediction")
    println("决策树回归r^2", 1- evaluator.evaluate(data_result))
  }
}

object 支持向量机分类模型 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = new LinearSVC()
      .setFeaturesCol("特征")
      .setLabelCol("好瓜2")
      .fit(data3)
    val data_result = model.transform(data3)
    data_result.show()
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("好瓜2")
      .setRawPredictionCol("rawPrediction")
    println("SVM分类AUC", evaluator.evaluate(data_result))
  }
}

object 朴素贝叶斯分类模型 extends LoadData{
  def main(args: Array[String]): Unit = {
    val model = new NaiveBayes()
      .setFeaturesCol("特征")
      .setLabelCol("好瓜2")
      .fit(data3)
    val data_result = model.transform(data3)
    data_result.show()
    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("好瓜2")
      .setRawPredictionCol("rawPrediction")
    println("朴素贝叶斯分类AUC", evaluator.evaluate(data_result))
  }
}
