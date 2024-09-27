package c56
package l5

object MyObject1 {
  def main(args: Array[String]): Unit = {
    def greeting(arr: Array[Int]) = {
      arr match {
        case Array(0) => println("只匹配元素为 0 的数组")
        case Array(0, _*) => println("匹配以 0 开头的数组，个数可以多个")
        case Array(x, y, z) => println(s"匹配三个元素的数组，并赋值：x=$x,y=$y,z=$z")
        case _ => println("什么都没有匹配上")
      }
    }

    greeting(Array(0))
    greeting(Array(2, 5, 7))
    greeting(Array(0, 4, 6))
    greeting(Array(3))

  }
}

object MyObject2 {
  def main(args: Array[String]): Unit = {
    // 匹配列表
    for (list <- Array(
      List(0),
      List(1, 3),
      List(2, 3, 0),
      List(1, 0, 0))) {
      val result = list match {
        case 0 :: Nil => "只有包含元素0的列表" //
        case x :: y :: Nil => "包含两个元素，并赋值为x和y：" + x + " " + y
        case 2 :: tail => "以 2 开头"
        case _ => "什么都不是"
      }
      println(result)
    }

  }
}

object MyObject3 {
  def main(args: Array[String]): Unit = {
    // 匹配元组
    for (pair <- Array(
      (0, 1),
      (1, 0),
      (2, 1),
      (1,0,2))) {
      val result = pair match {
        case (0, _) => "匹配以 0 开头的二元组"
        case (y, 0) => "匹配以0结尾的二元组并赋值给变量y："+y
        case (a,b) => "匹配二元组并赋值给a和b"+(b,a)
        case _ => "什么都没有匹配上"
      }
      println(result)
    }

  }
}

object MyObect4{
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4, "abc")
    l.collect{case a:Int =>a+10}.foreach(x=>println(x))
  }
}

object MyObject5{
  def main(args: Array[String]): Unit = {
    def f()={
      println("abc")
      "123"
    }
    lazy val fval = f
    println("pre f")
    println(f)
  }
}

object MyObject6{
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4,5,6,7)
    val square = (x:Int)=>(x*x).toString
    l.map(x=>(x*x).toString).mkString(",").foreach(print)
    println("")
    l.map(math.pow(_,2).toInt.toString).mkString(",").foreach(print)
    println("")
    l.map(square).mkString(",").foreach(print)

  }
}

object MyObject7 {
  def main(args: Array[String]): Unit = {
    val l = List("A,B,C", "D,E,F")
    l.map(_.split(",").toList).mkString(",").foreach(print)
    println("")
    l.map(_.split(",").toList).flatten.mkString(",").foreach(print)
  }
}
object MyObject8{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4,5)
    println(l.reduce((a,b) => {println((a,b));a+b}))
    println(l.reduce(_+_))
    println(l.sum)
    println(l.reduceLeft((a,b) => {println((a,b));a+b}))
    println(l.reduceRight((a,b) => {println((a,b));a+b}))
  }
}

object MyObject9 {
  def main(args: Array[String]): Unit = {
    val l = List(4,7,5,2,9,1,0)
    l.sorted.mkString(",").foreach(print)
    println("")
    val l2 = List((9,1),(8,2),(7,3),(6,4))
    l2.sortBy(_._1).mkString(",").foreach(print)
    println("")
    l2.sortBy(_._2).mkString(",").foreach(print)
    println("")
    l2.sortWith((a,b)=>a._1>b._1).mkString(",").foreach(print)
    println("")
    l2.sortWith((a,b)=>a._1<b._1).mkString(",").foreach(print)



  }
}

object MyObject10{
  def main(args: Array[String]): Unit = {
    val l = List(4,7,5,2,9,1,0)
    l.filter(_%2!=0).mkString(",").foreach(print)

  }
}

object MyObject11{
  def main(args: Array[String]): Unit = {
    val l1 = List(1,2,3,5,6,7)
    val l2 = List(3,4,5,6)
    l1.diff(l2).mkString(",").foreach(print)
    println("")
    l1.union(l2).mkString(",").foreach(print)
    println("")
    l1.intersect(l2).mkString(",").foreach(print)
    println("")
  }
}

object MyObject12{
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4,5,6,7)
    List(l.span(_<4)).mkString(",").foreach(print)
    println("")
    List(l.partition(_<4)).mkString(",").foreach(print)
    println(l.padTo(17, 0))
  }
}

object MyObject13{
  def main(args: Array[String]): Unit = {
    val l1 = List(1,2,3)
    val l2 = List("a","b","c")
    l1.zip(l2).mkString(",").foreach(print)
    println("")
    l2.zipWithIndex.mkString(",").foreach(print)
  }
}

