package nlp

import com.alibaba.fastjson.JSON
import org.ansj.splitWord.analysis.BaseAnalysis

import scala.io.Source
import scala.util.matching.Regex


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
  answer.content = x.getString("content")
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
      answer.content = x.getString("content")
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
//    println("用户自己的tag")
//    data.map(x=>(x.answerer_tags,1))
//      .groupBy(_._1)
//      .map(x=>(x._1,x._2.size))
//      .toList
//      .sortBy(_._2)
//      .reverse
//      .take(20)
//      .foreach(println)
//    println("title中关键词的分词后标签")
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>(x.title))
      .map(BaseAnalysis
        .parse(_)
        .getTerms
        .asScala
        .map(_.getName)
        .toList)
      .flatten
      .filter(_.size>1)
      .filter(x=>pattern.findFirstIn(x).size>0)
      .groupBy(x=>x)
      .map(x=>(x._1,x._2.size))
      .toList.sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

object 高频词 extends NlpBase {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>(x.content))
      .map(BaseAnalysis
        .parse(_)
        .getTerms
        .asScala
        .map(_.getName)
        .toList)
      .flatten
      .filter(_.size>1)
      .filter(x=>pattern.findFirstIn(x).size>0)
      .groupBy(x=>x)
      .map(x=>(x._1,x._2.size))
      .toList.sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

object 高频名词 extends NlpBase {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("[\\u4E00-\\u9FA5]+")
    data.map(x=>(x.content))
      .map(BaseAnalysis
        .parse(_)
        .getTerms
        .asScala
        .filter(_.getNatureStr=="n")
        .map(_.getName)
        .toList)
      .flatten
      .filter(_.size>1)
      .filter(x=>pattern.findFirstIn(x).size>0)
      .groupBy(x=>x)
      .map(x=>(x._1,x._2.size))
      .toList.sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

object 高赞用户 extends NlpBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.answerer_tags, x.star))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}
//哪个问题下的所有答案的赞的总和最多？
object 高赞问题 extends NlpBase {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.title, x.star))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

//标题平均字数
object 标题平均字数 extends NlpBase {
  def main(args: Array[String]): Unit = {
    var t = data.map(x=>x.title.length)
    println(t.sum.toDouble/t.size)
  }
}

object 统计不同长度标题的平均点赞数并排序 extends NlpBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.title.length,x.star))
      .groupBy(_._1)
      .map(x=>(x._1,{
        var r = x._2.map(_._2)
        r.sum.toDouble/r.size
      }))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

//难度2：在1的基础上，统计中尝试去掉异常值：
//去掉每个长度下点赞最多的和最少的
//去掉问题的回答少于3个的
object 统计不同长度标题的平均点赞数并排序难度2 extends NlpBase{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x.title.length,x.star))
      .groupBy(_._1)
      .filter(_._2.size>=3)
      .map(x=>(x._1,{
        var r = x._2.map(_._2)
        (r.sum-r.max-r.min).toDouble/(r.size-2)
      }))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}

//难度3：在2的基础上，统计不同长度区间的（如长11则统计10-12的）
// List() ++ List()
object 统计不同长度标题的平均点赞数并排序难度3 extends NlpBase{
  def main(args: Array[String]): Unit = {
//    var l = List(("a",1),("b",2),("b",2),("b",2),("b",2))
//    var l2 = l.map(_._2)
//    for(i<-Range(1,l.length)){
//      l2(i) = l2(i) + l2(i-1) + l2(i+1)
//    }
//    println(l)

    var d = data.map(x=>(x.title.length,x.star))
    (d ++ d.map(x=>(x._1-1,x._2)) ++ d.map(x=>(x._1+1,x._2)))
      .groupBy(_._1)
      .filter(_._2.size>=3)
      .map(x=>(x._1,{
        var r = x._2.map(_._2)
        (r.sum-r.max-r.min).toDouble/(r.size-2)
      }))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}
object 热点行业 extends NlpBase{
  def main(args: Array[String]): Unit = {
//    var l1 = data.filter(_.content.contains("计算机")).map(x=>"计算机")
//    var l2 = data.filter(_.content.contains("纺织")).map(x=>"纺织")
//    var l3 = data.filter(_.content.contains("金融")).map(x=>"金融")
    data.map(_.content)
      .map(x=>Set("计算机",
        "纺织",
        "金融",
        "农业",
        "房地产",
        "餐饮",
        "旅游").filter(x.contains))
      .filter(_.size>0)
      .flatten
      .groupBy(x=>x)
      .map(x=>(x._1,x._2.size))
      .toList.sortBy(_._2)
      .foreach(println)
  }
}

object 回答情感分析 extends NlpBase {
  def main(args: Array[String]): Unit = {
    var posDict = Source.fromFile("input/dict/正面词.dict")
      .getLines()
      .toSet
    var negDict = Source.fromFile("input/dict/负面词.dict")
      .getLines()
      .toSet

    var md = data.map(x=>(x.qid+"_"+x.title+"_"+x.answer_id, x.content))
      .map(x=>
        BaseAnalysis
          .parse(x._2)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
          .map(y=>(x._1,y))
      )
      .flatten
      .map(x=>(x._1,{
        if(posDict.contains(x._2)) 1
        else if (negDict.contains(x._2)) -1
        else 0
      }))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .reverse
    println("最正面",md.take(10))
    println("最负面",md.takeRight(10))

  }
}

object 问题情感分析 extends NlpBase {
  def main(args: Array[String]): Unit = {
    var posDict = Source.fromFile("input/dict/正面词.dict")
      .getLines()
      .toSet
    var negDict = Source.fromFile("input/dict/负面词.dict")
      .getLines()
      .toSet

    var md = data.map(x=>(x.qid+"_"+x.title, x.content))
      .map(x=>
        BaseAnalysis
          .parse(x._2)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
          .map(y=>(x._1,y))
      )
      .flatten
      .map(x=>(x._1,{
        if(posDict.contains(x._2)) 1
        else if (negDict.contains(x._2)) -1
        else 0
      }))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .reverse
    println("最正面",md.take(10))
    println("最负面",md.takeRight(10))

  }
}

object 用户情感分析 extends NlpBase {
  def main(args: Array[String]): Unit = {
    var posDict = Source.fromFile("input/dict/正面词.dict")
      .getLines()
      .toSet
    var negDict = Source.fromFile("input/dict/负面词.dict")
      .getLines()
      .toSet

    var md = data.map(x=>(x.answerer_tags, x.content))
      .map(x=>
        BaseAnalysis
          .parse(x._2)
          .getTerms
          .asScala
          .map(_.getName)
          .toList
          .map(y=>(x._1,y))
      )
      .flatten
      .map(x=>(x._1,{
        if(posDict.contains(x._2)) 1
        else if (negDict.contains(x._2)) -1
        else 0
      }))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(_._2)
      .reverse
    println("最正面",md.take(10))
    println("最负面",md.takeRight(10))

  }
}

