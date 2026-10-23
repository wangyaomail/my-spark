package c12

import com.alibaba.fastjson.JSON
import org.ansj.splitWord.analysis.BaseAnalysis


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

object 标题平均字数 extends QaData {
  def main(args: Array[String]): Unit = {
    val d = data.map(x=>(x.title.length,x))
    println("平均字数", d.map(_._1).sum.toDouble/d.length)
    println(data.map(_.content.length).max)
  }
}

//不同长度标题的平均点赞数并
object 不同长度标题的平均点赞数并1 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.title.length, x.star))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.map(_._2).sum.toDouble/x._2.length, x._2.length))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

//难度2：在1的基础上，统计中尝试去掉异常值：
//去掉每个长度下点赞最多的和最少的
//去掉问题的回答少于3个的
object 不同长度标题的平均点赞数并2 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.title.length, x.star))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.sortBy(_._1).drop(1).dropRight(1)))
      .filter(_._2.length>2)
      .map(x=>(x._1, x._2.map(_._2).sum.toDouble/x._2.length, x._2.length))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

//在2的基础上，统计不同长度区间的（如长11则统计10-12的）
object 不同长度标题的平均点赞数并3 extends QaData {
  def main(args: Array[String]): Unit = {
    val d1 = data.map(x=>(x.title.length, x.star, x))
    val d2 = d1.map(x=>(x._1+1, x._2, x._3))
    val d3 = d1.map(x=>(x._1-1, x._2, x._3))
    List.concat(d1, d2, d3)
      .groupBy(_._1)
      .map(x=>(x._1,x._2.sortBy(_._1).drop(1).dropRight(1)))
      .filter(_._2.length>2)
      .map(x=>(x._1, x._2.map(_._2).sum.toDouble/x._2.length, x._2.length, x._2.take(3).map(_._3)))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

// 制造，零售，金融，地产，教育，医疗，物流，餐饮，文旅，能源，通信，传媒，建筑，农林，化工，汽车，纺织，矿业，软件，军工
object 热点行业 extends QaData {
  def main(args: Array[String]): Unit = {
    val l = "制造，零售，金融，地产，教育，医疗，物流，餐饮，文旅，能源，通信，传媒，建筑，农林，化工，汽车，纺织，矿业，软件，军工"
      .split("，").toList
    data.map(x=>x.content)
      .map(x=>{
        l.map(y=>(
          y,
          if(x.contains(y)) 1 else 0
          ))
      })
      .flatten
      .groupBy(_._1)
      .map(x=>(x._1, x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

import scala.collection.JavaConverters._
object 高频词 extends QaData {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>x.content)
      .map(x=>{
        BaseAnalysis.parse(x)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
          .filter(_.length>=2)
          .filter(pattern.findFirstIn(_).size>0)
      })
      .flatten
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

object 高频词取名词 extends QaData {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>x.content)
      .map(x=>{
        BaseAnalysis.parse(x)
          .getTerms
          .asScala
          .filter(_.getNatureStr.startsWith("n"))
          .map(_.getName)
          .toList
          .filter(_.length>=2)
          .filter(pattern.findFirstIn(_).size>0)
      })
      .flatten
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

object 高频用户 extends QaData {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>x.answerer_tags)
      .map(x=>{
        BaseAnalysis.parse(x)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
          .filter(_.length>=2)
          .filter(pattern.findFirstIn(_).size>0)
      })
      .flatten
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}

object 情感分析 extends QaData {
  def main(args: Array[String]): Unit = {
    val posDict = Source.fromFile("input/dict/正面词.dict")
      .getLines()
      .filter(_.length>0)
      .toSet
    val negDict = Source.fromFile("input/dict/负面词.dict")
      .getLines()
      .filter(_.length>0)
      .toSet

    data.map(x=>(x.content, x))
      .map(x=>{
        (BaseAnalysis.parse(x._1)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
        ,x._2)
      })
      .map(x=>{
        (x._1.map(y=>{
        if(posDict.contains(y)) 1
        else if(negDict.contains(y)) -1
        else 0
      }).sum,x._2)
      })
      .sortBy(_._1)
      .filter(x=>Math.abs(x._1)>10)
      .foreach(println)
  }
}

object 正负中立答案数量 extends QaData {
  def main(args: Array[String]): Unit = {
    val posDict = Source.fromFile("input/dict/正面词.dict")
      .getLines()
      .filter(_.length>0)
      .toSet
    val negDict = Source.fromFile("input/dict/负面词.dict")
      .getLines()
      .filter(_.length>0)
      .toSet

    data.map(x=>(x.content, x))
      .map(x=>{
        (BaseAnalysis.parse(x._1)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
          ,x._2)
      })
      .map(x=>{
        (x._1.map(y=>{
          if(posDict.contains(y)) 1
          else if(negDict.contains(y)) -1
          else 0
        }).sum,x._2)
      })
      .map(x=>(
        if(x._1>0) "正面"
        else if (x._1<0) "负面"
        else "中性"
        ,1)
      )
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println)
  }
}

object 正负中立用户 extends QaData {
  def main(args: Array[String]): Unit = {
    val posDict = Source.fromFile("input/dict/正面词.dict")
      .getLines()
      .filter(_.length>0)
      .toSet
    val negDict = Source.fromFile("input/dict/负面词.dict")
      .getLines()
      .filter(_.length>0)
      .toSet

    data.map(x=>(x.content, x))
      .map(x=>{
        (BaseAnalysis.parse(x._1)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
          ,x._2)
      })
      .map(x=>{
        (x._1.map(y=>{
          if(posDict.contains(y)) 1
          else if(negDict.contains(y)) -1
          else 0
        }).sum,x._2.answerer_tags, x._2)
      })
      .groupBy(_._2)
      .map(x=>(x._1, x._2.map(_._1).sum, x._2.map(_._3).toList(0)))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}


