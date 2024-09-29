package c56
package l7

import com.alibaba.fastjson.JSON

class Answer(var qid: Int, var title: String, var desc: String, var topic: String, var star: Int, var content: String, var answer_id: String, var answer_tags: String) {
  override def toString()={
    qid+":"+title+":"+answer_id
  }
}

object obj1 {
  def main(args: Array[String]): Unit = {
    val data = scala.io.Source
      .fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json","utf8")
      .getLines().toList
    println(data.map(JSON.parseObject(_))
      .filter(_.containsKey("qid"))
      .size)
    val data2 = data.map(JSON.parseObject(_))
    Array("qid","title","desc","topic","star","content","answer_id","answerer_tags")
      .map(key=>(key, data2.filter(x=>x.containsKey(key)).size))
      .foreach(println)
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

object obj2 {
  def main(args: Array[String]): Unit = {
    new LoadData().data
      .map(_.title)
      .foreach(println)
  }
}

object 热点问题{
  def main(args: Array[String]): Unit = {
    val data = new LoadData()
    println(data.data.map(x=>x.qid+"-"+x.answer_id)
      .distinct.size)
    data.data.groupBy(_.qid)
      .map(x=>(x._1, x._2.length, x._2(0).title))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println)
  }
}






