package c34
package l06

import com.alibaba.fastjson.JSON
import org.ansj.splitWord.analysis.BaseAnalysis

import scala.io.Source
import scala.util.matching.Regex

object 加载数据 {
  def main(args: Array[String]): Unit = {
    Source.fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
      .getLines()
      .map(x=>JSON.parseObject(x))
      .map(x=>(
        x.getString("title"),
        "https://www.zhihu.com/question/"+x.get("qid")+"/answer/"+x.get("answer_id")
        ))
      .foreach(println)
  }
}

object 字段缺失 {
  def main(args: Array[String]): Unit = {
    val res = Source.fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
      .getLines()
      .map(x=>JSON.parseObject(x))
      .map(x=>(
        if(x.containsKey("qid")) 1 else 0,
        if(x.containsKey("title")) 1 else 0,
        if(x.containsKey("desc")) 1 else 0,
        if(x.containsKey("topic")) 1 else 0,
        if(x.containsKey("star")) 1 else 0,
        if(x.containsKey("content")) 1 else 0,
        if(x.containsKey("answer_id")) 1 else 0,
        if(x.containsKey("answerer_tags")) 1 else 0,
      ))
      .reduce((a,b)=>(
        a._1+b._1,
        a._2+b._2,
        a._3+b._3,
        a._4+b._4,
        a._5+b._5,
        a._6+b._6,
        a._7+b._7,
        a._8+b._8,
        ))
    println(res)
  }
}

class Answer {
  var qid = ""
  var title = ""
  var desc = ""
  var topic = ""
  var star = 0
  var content = ""
  var answer_id = ""
  var answerer_tags = ""

  def link = {
    "https://www.zhihu.com/question/"+qid+"/answer/"+answer_id
  }
  override def toString: String = {
    title+link

  }
}

class QaData {
  val data = Source.fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json")
    .getLines()
    .map(x=>JSON.parseObject(x))
    .map(x=>{
      var ans = new Answer
      ans.qid = x.getString("qid")
      ans.title = x.getString("title")
      ans.desc = x.getString("desc")
      ans.star = x.getInteger("star")
      ans.content = x.getString("content")
      ans.answer_id = x.getString("answer_id")
      ans.answerer_tags = x.getString("answerer_tags")
      ans
    })
    .toList
}

object 检查 extends QaData {
  def main(args: Array[String]): Unit = {
    data.foreach(println)
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
        x._2.size,
        x._2.map(_._1).sum,
        x._2(0)._3.title,
        x._2(0)._3.link,
      ))
      .toList
      .sortBy(_._3)
      .reverse
      .take(100)
      .foreach(println)
  }
}

object 热点问题2 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.star,x.qid,x))
      .groupBy(_._2)
      .map(x=>(x._1,
        x._2.size,
        x._2.map(_._1).sum,
        x._2(0)._3.title,
        x._2(0)._3.link,
      ))
      .toList
      .sortBy(_._2)
      .reverse
      .take(100)
      .foreach(println)
  }
}

object 热点标签 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.answerer_tags,x))
      .groupBy(_._1)
      .map(x=>(x._1,
        x._2.size,
        x._2.toList.take(3)))
      .toList
      .sortBy(_._2)
      .reverse
      .take(100)
      .foreach(println)
  }
}

object 热点标签2 extends QaData {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>(x.answerer_tags,x))
      .filter(_._1.size>3)
      .filter(x=>pattern.findFirstIn(x._1).size>0)
      .map(x=>(x._1.substring(0,4), x._2))
      .groupBy(_._1)
      .map(x=>(x._1,
        x._2.size,
        x._2.toList.take(3)))
      .toList
      .sortBy(_._2)
      .reverse
      .take(100)
      .foreach(println)
  }
}

object 高赞用户 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.star,x.answerer_tags,x))
      .groupBy(_._2)
      .map(x=>(x._1,
        x._2.size,
        x._2.map(_._1).sum,
        x._2(0)._3.title,
        x._2(0)._3.link,
      ))
      .toList
      .sortBy(_._3)
      .reverse
      .take(100)
      .foreach(println)
  }
}

object 标签切分 extends QaData {
  def main(args: Array[String]): Unit = {
//    data.map(x=>(x.title + x.content, x))
//      .take(100)
//      .map(x=>{
//        BaseAnalysis.parse(x._1).getTerms().toArray().toList
////          .map(_.)
//      })
////      .filter(_._1.size>3)
////      .filter(x=>pattern.findFirstIn(x._1).size>0)
////      .map(x=>(x._1.substring(0,4), x._2))
////      .groupBy(_._1)
////      .map(x=>(x._1,
////        x._2.size,
////        x._2.toList.take(3)))
////      .toList
////      .sortBy(_._2)
////      .reverse
////      .take(100)
//      .foreach(println)
  }
}


