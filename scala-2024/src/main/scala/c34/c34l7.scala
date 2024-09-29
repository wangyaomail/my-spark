package c34
package l7

import com.alibaba.fastjson.JSON

class LoadData {
  val data = scala.io.Source
    .fromFile("input/students.data","utf8")
    .getLines()
    .toList
    .map(_.trim().split("\t"))
    .filter(_.length==8)
}
object obj1 extends LoadData {
  def main(args: Array[String]): Unit = {
    data.map(_(0).substring(0,1))
      .distinct.foreach(println)
  }
}

object obj2 extends LoadData{
  def main(args: Array[String]): Unit = {
    val data2 = data.map(2024-_(4).substring(0,4).toInt)
    println(data2.sum/data2.length)
  }
}

object obj3 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(2024-_(4).substring(0,4).toInt)
      .map(x=>(x, 1))
      .groupBy(_._1)
      .map(x=>(x._1, x._2.length))
      .foreach(println)
  }
}

object obj4 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(_(5).toLong)
      .sorted
      .reverse
      .foreach(println)
  }
}


object obj5 extends LoadData{
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).substring(5), x(0)))
      .groupBy(_._1)
      .mapValues(_.map(_._2))
      .foreach(println)
  }
}

object obj6 extends LoadData {
  def main(args: Array[String]): Unit = {
    data.map(x=>(x(4).replace("-","").toInt,x(0)))
      .sortBy(_._1)
      .reverse
      .take(5)
      .foreach(println)
  }
}


object obj7 {
  def main(args: Array[String]): Unit = {
    scala.io.Source
      .fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json", "utf8")
      .getLines()
      .toList
      .take(3)
      .map(x=>JSON.parseObject(x))
      .map(_.getString("title"))
      .foreach(println)
  }
}


class Answer(var qid:Int,
             var title:String,
             var desc:String,
             var topic:String,
             var star:Int,
             var content:String,
             var answer_id:String,
             var answerer_tags:String
            ){
  override def toString: String = {
    qid+":"+title+":"+answer_id
  }
}

class LoadQA {
  val data = scala.io.Source
    .fromFile("C:\\share\\data\\webtext2019zh\\web_text_zh_test.json", "utf8")
    .getLines()
    .map(x=>JSON.parseObject(x))
    .toList
  val data2 = data.map(x=>new Answer(
    x.getIntValue("qid"),
    x.getString("title"),
    x.getString("desc"),
    x.getString("topic"),
    x.getIntValue("star"),
    x.getString("content"),
    x.getString("answer_id"),
    x.getString("answerer_tags")
  ))
}

object obj8 extends LoadQA{
  def main(args: Array[String]): Unit = {
    List("qid","title","desc","topic","star","content","answer_id","answerer_tags")
      .map(keyname=>(keyname, data
        .filter(x=>x.containsKey(keyname))
        .length)
      )
      .foreach(println)
  }
}

object obj9 extends LoadQA{
  def main(args: Array[String]): Unit = {
    data2.take(3).foreach(println)
  }
}

object 热点问题 extends LoadQA{
  def main(args: Array[String]): Unit = {
    val data3 = data2
//      .map(x=>(x.qid, x.title)
      .groupBy(_.qid)
      .map(x=>(x._1, x._2.length, x._2(0).title))
      .toList
      .sortBy(_._2)
    println("最热的3个问题：")
    data3.takeRight(3).foreach(println)
    println("最冷的3个问题：")
    data3.take(3).foreach(println)
    println("平均回答：")
    println(data3.map(_._2).sum.toDouble/data3.length)

  }
}
