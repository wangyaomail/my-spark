package c78
package t1

import com.alibaba.fastjson.JSON
import org.ansj.splitWord.analysis.BaseAnalysis

import scala.io.Source


class Answer {
  var qid = ""
  var answer_id = ""
  var title = ""
  var desc = ""
  var topic = ""
  var star = 0
  var content = ""
  var answerer_tags = ""
  override def toString()={
    qid+":"+title+":"+answer_id
  }
}


object 载入数据集 {
  def main(args: Array[String]): Unit = {
    val filePath = "C:\\share\\data\\webtext2019zh\\web_text_zh_test.json"
    var data = Source.fromFile(filePath)
      .getLines()
      .toList
      .map(JSON.parseObject(_))
//      .map(x=>(x.get("qid"),x.get("title")))
      .map(x=>{
            var answer = new Answer
            answer.qid = x.getString("qid")
  answer.answer_id = x.getString("answer_id")
  answer.title = x.getString("title")
  answer.desc = x.getString("desc")
  answer.topic = x.getString("topic")
  answer.star = x.getIntValue("star")
  answer.content = x.getString("qcontentd")
  answer.answerer_tags = x.getString("answerer_tags")
            answer
          })
    data.foreach(println)
  }
}

class NlpBase {
  val filePath = "C:\\share\\data\\webtext2019zh\\web_text_zh_test.json"
  var data = Source.fromFile(filePath)
    .getLines()
    .toList
    .map(JSON.parseObject(_))
    .map(x=>{
      var answer = new Answer
      answer.qid = x.getString("qid")
      answer.answer_id = x.getString("answer_id")
      answer.title = x.getString("title")
      answer.desc = x.getString("desc")
      answer.topic = x.getString("topic")
      answer.star = x.getIntValue("star")
      answer.content = x.getString("qcontentd")
      answer.answerer_tags = x.getString("answerer_tags")
      answer
    })
}

object XXX extends NlpBase {
  def main(args: Array[String]): Unit = {
    data.take(3).foreach(println)
    println(data.filter(_.answer_id.equals("")).size)
  }
}

object 热点问题 extends NlpBase{
  def main(args: Array[String]): Unit = {
    println("问题下回答数量多")
    data.map(x=>(x.qid+":"+x.title,x.answer_id))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .reverse
      .take(20)
      .foreach(println)

    println("问题下所有回答的点赞数量多")
    data.map(x=>(x.qid+":"+x.title,x.star))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .reverse
      .take(20)
      .foreach(println)
  }
}

import scala.collection.JavaConverters._
object 热点标签 extends NlpBase {
  def main(args: Array[String]): Unit = {
    println("用户自己的tag")
    data.map(x=>(x.answerer_tags,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .reverse
      .take(20)
//      .foreach(println)
    println("title中关键词的分词后标签")
    data.map(x=>(x.title))
      .map(BaseAnalysis
        .parse(_)
        .getTerms
        .asScala
        .map(_.getName)
        .toList)
      .take(5)
      .foreach(println)
  }
}