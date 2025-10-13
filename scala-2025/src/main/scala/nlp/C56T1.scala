package c56
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
  def toZhihuUrl(): String = {
    "https://www.zhihu.com/question/"+qid+"/answer/"+answer_id
  }

  override def toString: String = {
    qid+":"+title+":"+answer_id
  }
}

object 加载数据集 {
  def main(args: Array[String]): Unit = {
    var filepath = "C:\\share\\data\\webtext2019zh\\web_text_zh_test.json"
    var data = Source.fromFile(filepath)
      .getLines()
      .map(JSON.parseObject(_))
      .map(x=>{
        var answer = new Answer
        answer.qid = x.getString("qid")
        answer.answer_id = x.getString("answer_id")
        answer.title = x.getString("title")
        answer.desc = x.getString("desc")
        answer.topic = x.getString("topic")
        answer.star = x.getIntValue("star")
        answer.content = x.getString("content")
        answer.answerer_tags = x.getString("answerer_tags")
        answer
      })
      .take(5)
      .foreach(println)
  }
}


class NlpBase {
  var filepath = "C:\\share\\data\\webtext2019zh\\web_text_zh_test.json"
  var data = Source.fromFile(filepath)
    .getLines()
    .map(JSON.parseObject(_))
    .map(x=>{
      var answer = new Answer
      answer.qid = x.getString("qid")
      answer.answer_id = x.getString("answer_id")
      answer.title = x.getString("title")
      answer.desc = x.getString("desc")
      answer.topic = x.getString("topic")
      answer.star = x.getIntValue("star")
      answer.content = x.getString("content")
      answer.answerer_tags = x.getString("answerer_tags")
      answer
    })
}
object 热点问题 extends NlpBase {
  def main(args: Array[String]): Unit = {
    // 按回答数量
//    data.map(x=>(x.qid+":"+x.title, x.answer_id))
//      .toList
//      .groupBy(_._1)
//      .map(x=>(x._1,x._2.size))
//      .toList
//      .sortBy(_._2)
//      .reverse
//      .take(10)
//      .foreach(println)

    // 按总的点赞数量
    data.map(x=>(x.qid+":"+x.title, x.star))
      .toList
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

import scala.collection.JavaConverters._
object 热点标签 extends NlpBase {
  def main(args: Array[String]): Unit = {
    // 按topic
//    data.map(x=>(x.topic,1))
//      .toList
//      .groupBy(_._1)
//      .map(x=>(x._1,x._2.size))
//      .toList
//      .sortBy(_._2)
//      .reverse
//      .take(5)
//      .foreach(println(_))

    // 按分词后的标签
    data.map(x=>x.title)
      .map(BaseAnalysis
        .parse(_)
        .getTerms
        .asScala
        .map(_.getName)
        .toList
        .filter(_.length>1)
      )
      .flatten
      .map((_,1))
      .toList
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}


