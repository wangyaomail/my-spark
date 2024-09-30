package c56
package l8

import com.alibaba.fastjson.JSON
import org.ansj.splitWord.analysis.BaseAnalysis

import scala.util.matching.Regex

class Answer(var qid: Int, var title: String, var desc: String, var topic: String, var star: Int, var content: String, var answer_id: String, var answer_tags: String) {
  override def toString()={
    qid+":"+title+":"+answer_id
  }
}

class LoadData {
  val data = scala.io.Source
    .fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json","utf8")
    .getLines()
    .toList
    .map(JSON.parseObject(_))
    .map(x=>new Answer(
      x.getIntValue("qid"),
      x.getString("title"),
      x.getString("desc"),
      x.getString("topic"),
      x.getIntValue("star"),
      x.getString("content"),
      x.getString("answer_id"),
      x.getString("answerer_tags")
    ))
  val qmap = data.map(x=>(x.qid, x)).toMap
  val amap = data.map(x=>(x.answer_id, x)).toMap
}

object 热点标签1 extends LoadData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.answer_tags,1))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.length))
      .toList.sortBy(_._2)
      .foreach(println)

  }
}

object 热点标签2 extends LoadData {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>x.answer_tags)
      .map(x=>if(x.length>3) x.substring(0,3) else x)
      .filter(x=>pattern.findAllIn(x).size>0)
      .map(x=>(x,1))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.length))
      .toList.sortBy(_._2)
      .reverse
      .take(30)
      .foreach(println)

  }
}

object 高赞用户 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.answer_tags,x.star))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.map(_._2).sum))
      .toList.sortBy(_._2)
      .reverse
      .take(30)
      .foreach(println)

  }
}

object 高频词 extends LoadData{
  def main(args: Array[String]): Unit = {
    import scala.collection.JavaConverters._
    data.map(_.content)
      .map(x=>BaseAnalysis.parse(x).getTerms.asScala
        .filter(_.getNatureStr.startsWith("n"))
        .map(_.getName))
      .flatten
      .filter(_.length>1)
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.map(_._2).length))
      .toList.sortBy(_._2).reverse
      .take(30)
      .foreach(println)
  }
}

object 情感分析 extends LoadData{
  def main(args: Array[String]): Unit = {
    val pos_dict = scala.io.Source
      .fromFile("input/dict/正面词.dict")
      .getLines()
      .toSet
    val neg_dict = scala.io.Source
      .fromFile("input/dict/负面词.dict")
      .getLines()
      .toSet
    import scala.collection.JavaConverters._
    val data2 = data.map(x=>(x.content, x.title))
      .map(x=>(BaseAnalysis.parse(x._1).getTerms.asScala
              .map(_.getName), x._2, x._1)
      )
      .map(x=>(
        x._1.map(
          y=> if(pos_dict.contains(y)) 1
          else if(neg_dict.contains(y)) -1
          else 0
        ), x._2, x._3)
      )
      .map(x=>(x._1.sum, x._2, x._3))
      .sortBy(_._1)
      .reverse
    println("最正面的3个回答")
    data2.take(3).foreach(println)
    println("最负面的3个回答")
    data2.takeRight(3).foreach(println)
  }
}

