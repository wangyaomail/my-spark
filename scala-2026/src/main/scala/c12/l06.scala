package c12

import com.alibaba.fastjson.JSON

import scala.io.Source
import scala.util.matching.Regex

object 加载数据 {
  def main(args: Array[String]): Unit = {
    Source.fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
      .getLines()
      .map(x=>JSON.parseObject(x))
      .foreach(println)
  }
}

class Answer {
  var qid = ""
  var title=""
  var desc=""
  var topic=""
  var star=0
  var content=""
  var answer_id=""
  var answerer_tags=""

  override def toString: String = {
    title+":https://www.zhihu.com/question/"+qid+"/answer/"+answer_id
  }
}

class QaData {
  val src = Source.fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
    .getLines()
    .map(x=>JSON.parseObject(x))
    .toList
  val data = src
    .map(x=>{
      val answer = new Answer
      answer.qid = x.getString("qid")
      answer.title = x.getString("title")
      answer.desc = x.getString("desc")
      answer.topic = x.getString("topic")
      answer.star = x.getInteger("star")
      answer.content = x.getString("content")
      answer.answer_id = x.getString("answer_id")
      answer.answerer_tags = x.getString("answerer_tags")
      answer
    }).toList
}

object 数据检查 extends QaData {
  def main(args: Array[String]): Unit = {
    val res = src.map(x=>(
        if(x.containsKey("qid")) 1 else 0,
        if(x.containsKey("title")) 1 else 0,
        if(x.containsKey("desc")) 1 else 0,
        if(x.containsKey("topic")) 1 else 0,
        if(x.containsKey("star")) 1 else 0,
        if(x.containsKey("content")) 1 else 0,
        if(x.containsKey("answer_id")) 1 else 0,
        if(x.containsKey("answerer_tags")) 1 else 0,
        ))
      .reduce((x,y)=>(
        x._1+y._1,
        x._2+y._2,
        x._3+y._3,
        x._4+y._4,
        x._5+y._5,
        x._6+y._6,
        x._7+y._7,
        x._8+y._8,
      ))
    println(res)
  }
}

object 热点答案 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.star,x))
      .sortBy(_._1)
      .reverse
      .take(10)
      .foreach(println)
  }
}

object 热点问题 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.star,x.qid,x))
      .groupBy(_._2)
      .map(x=>(x._1,
        x._2.map(_._1).sum,
        x._2(0)._3))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}


object 热点问题2 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.star,x.qid,x))
      .groupBy(_._2)
      .map(x=>(x._1,
        x._2.length,
        x._2.map(_._1).sum/ x._2.map(_._1).length,
        x._2(0)._3))
      .toList
      .sortBy(_._3)
      .reverse
      .take(10)
      .foreach(println)
  }
}


object 热点标签 extends QaData {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>(x.answerer_tags,x))
      .filter(x=> pattern.findAllIn(x._1).size>0)
      .filter(x=> x._1.size>3)
      .map(x=>(x._1.substring(0,4), x._2))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.length))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

object 高赞用户 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.star,x.answerer_tags,x))
      .groupBy(_._2)
      .map(x=>(x._1,
        x._2.map(_._1).sum,
        x._2(0)._3))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

