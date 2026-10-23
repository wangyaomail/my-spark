package c34
package l06

import com.alibaba.fastjson.JSON
import org.ansj.domain.Term
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

object 标题平均字数 extends QaData {
  def main(args: Array[String]): Unit = {
    val d = data.map(x=>(x.title, x))
      .map(x=>(x._1.length, x._2))
      println(d.map(_._1).sum.toDouble/d.length)
    println("最长标题",d.map(_._1).max, d.sortBy(_._1).reverse.take(1))
  }
}

//统计不同长度标题的平均点赞数并排序
object 标题的最合适长度1 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.title.length, x.star, x))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.map(_._2).sum.toDouble/x._2.length, x._2.length))
      .toList
      .sortBy(_._2)
      .reverse
      .foreach(println)
  }
}

//难度2：在1的基础上，统计中尝试去掉异常值：
//去掉每个长度下点赞最多的和最少的
//去掉问题的回答少于3个的
object 标题的最合适长度2 extends QaData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.title.length, x.star, x))
      .groupBy(_._1)
      .map(x=>(x._1,x._2
        .sortBy(_._2)
        .drop(1).dropRight(1)
      ))
      .map(x=>(x._1, x._2.map(_._2).sum.toDouble/x._2.length, x._2.length))
      .filter(x=>x._3>=3)
      .toList
      .sortBy(_._2)
      .reverse
      .foreach(println)
  }
}

//难度3：在2的基础上，统计不同长度区间的（如长11则统计10-12的）
object 标题的最合适长度3 extends QaData {
  def main(args: Array[String]): Unit = {
    val d1 = data.map(x=>(x.title.length, x.star, x))
    val d2 = d1.map(x=>(x._1+1, x._2, x._3))
    val d3 = d1.map(x=>(x._1-1, x._2, x._3))
    List.concat(d1, d2, d3)
      .groupBy(_._1)
      .map(x=>(x._1,x._2
        .sortBy(_._2)
        .drop(1).dropRight(1)
      )).toList
      .map(x=>(x._1, x._2.map(_._2).sum.toDouble/x._2.length, x._2.length))
      .filter(x=>x._3>=3)
      .toList
      .sortBy(_._2)
      .reverse
      .foreach(println)
  }
}

// 农业，林业，牧业，渔业，采矿，制造，电力，建筑，批发，零售，交通，仓储，邮政，住宿，餐饮，信息，金融，地产，租赁，商务，科研，水利，环境，居民，教育，卫生，文化，体育，娱乐，社保

object 热点行业 extends QaData{
  def main(args: Array[String]): Unit = {
    val l = "农业，林业，牧业，渔业，采矿，制造，电力，建筑，批发，零售，交通，仓储，邮政，住宿，餐饮，信息，金融，地产，租赁，商务，科研，水利，环境，居民，教育，卫生，文化，体育，娱乐，社保".split("，")
      .toList
    data.map(x=>(x.content, x))
      .map(x=>l.map(y=>(y, if(x._1.contains(y)) 1 else 0)))
      .flatten
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
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
      .map(x=>BaseAnalysis
        .parse(x)
        .getTerms
        .asScala
        .toList
        .map(_.getName)
        .filter(_.length>1)
        .filter(pattern.findFirstIn(_).size>0)
      )
      .flatten
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .foreach(println)
  }
}


object 高频名词 extends QaData {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>x.content)
      .map(x=>BaseAnalysis
        .parse(x)
        .getTerms
        .asScala
        .toList
        .filter(_.getNatureStr.startsWith("n"))
        .map(_.getName)
        .filter(_.length>1)
        .filter(pattern.findFirstIn(_).size>0)
      )
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
    data.map(x=>x.answerer_tags)
      .map(x=>BaseAnalysis
        .parse(x)
        .getTerms
        .asScala
        .toList
        .filter(_.getNatureStr.startsWith("n"))
        .map(_.getName)
        .filter(_.length>1)
      )
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
    data.map(x=>(x.content,x))
      .map(x=>(BaseAnalysis
        .parse(x._1)
        .getTerms
        .asScala
        .toList
        .map(_.getName)
        .filter(_.length>1),
        x._2)
      )
      .map(x=>(
        x._1
        .map(y=>{
          if(posDict.contains(y)) 1
          else if(negDict.contains(y)) -1
          else 0
        }).sum,
        x._2
      ))
      .toList
      .filter(x=>Math.abs(x._1)>10)
      .sortBy(_._1)
      .foreach(println(_))

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
    data.map(x=>(x.content,x))
      .map(x=>(BaseAnalysis
        .parse(x._1)
        .getTerms
        .asScala
        .toList
        .map(_.getName)
        .filter(_.length>1),
        x._2)
      )
      .map(x=>(
        x._1
          .map(y=>{
            if(posDict.contains(y)) 1
            else if(negDict.contains(y)) -1
            else 0
          }).sum,
        x._2
      ))
      .map(x=>(
        if(x._1>0) "正面"
        else if(x._1<0) "负面"
        else "中性"
        ,x._2
      ))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .foreach(println(_))
  }
}

