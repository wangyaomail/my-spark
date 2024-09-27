package c34
package l5

object obj1{
  def main(args: Array[String]): Unit = {
    def greeting(arr: Array[Int]) {
      arr match {
        case Array(0) => println("只匹配元素为 0 的数组")
        case Array(x, y, z) => println(s"匹配三个元素的数组，并赋值：x=$x,y=$y,z=$z")
        case Array(0, _*) => println("匹配以 0 开头的数组，个数可以多个")
        case _ => println("什么都没有匹配上")
      }
    }
    greeting(Array(0))
    greeting(Array(2,5,7))
    greeting(Array(0,4,6,1))
    greeting(Array(3))
  }
}

object obj2 {
  def main(args: Array[String]): Unit = {
    // 匹配列表
    for (list <- Array(
      List(0),
      List(1, 3),
      List(2, 3, 0),
      List(1, 0, 0))) {
      val result = list match {
        case 0 :: Nil => "只有包含元素0的列表" //
        case x :: y :: Nil => "包含两个元素，并赋值为x和y："+x + " " + y
        case 2 :: tail => "以 2 开头"
        case _ => "什么都不是"
      }
      println(result)
    }
  }
}

object obj3{
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

object obj4{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4, "abc")
    l.collect{case x:Int=>x+10}.foreach(println)

  }
}

object obj5{
  def main(args: Array[String]): Unit = {
    def f={
      println("1")
      "4"
    }
    println("2")
    lazy val f2 = f
    println("3")
    println(f2)
  }
}

object obj6{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4)
    l.map(_+10).mkString(",").foreach(print)
    def f(x:Int) = {
      x+10
    }
    println("")
    l.map(f(_)).mkString(",").foreach(print)
  }
}

object obj7{
  def main(args: Array[String]): Unit = {
    val l = List("a,b,c", "1,2,3")
    val data = l.map(_.split(",").toList)
    println(data)
    println(data.flatten)
  }
}

object obj8{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4,5)
    println(l.reduce(_+_))
    println(l.reduce((a,b)=>{println(a,b); a+b}))
  }
}

object obj9{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4,5)
    println(l.fold(10)(_+_))
    println(l.foldRight(10)((a,b)=>{println(a,b); a+b}))
  }
}

object obj10{
  def main(args: Array[String]): Unit = {
    val l = List(4,8,2,5,0,1)
    l.sorted.mkString(",").foreach(print)
    println("")
    val l2 = List(("a",1),("b",3),("c", 0))
    l2.sortBy(_._2).mkString(",").foreach(print)
    println("")
    l.sortWith(_<_).mkString(",").foreach(print)
    println("")


  }
}

object obj11{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4,5,6,7,8)
    l.filter(_%2==1).mkString(",").foreach(print)
    println("")
  }
}

object obj12{
  def main(args: Array[String]): Unit = {
    val l1 = List(1,2,3,5,6,7)
    val l2 = List(3,4,5,6,7,8,9)
    l1.diff(l2).mkString(",").foreach(print)
    println("")
    l1.union(l2).mkString(",").foreach(print)
    println("")
    l1.intersect(l2).mkString(",").foreach(print)
    println("")
  }
}

object obj13{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3)
    l.scan(10)(_+_).mkString(",").foreach(print)
    println("")
  }
}

object obj14{
  def main(args: Array[String]): Unit = {
    val l = List(2,4,6,8,1,3,5,7, 2,2,2,2)
    l.take(3).mkString(",").foreach(print)
    println("")
    l.takeWhile(_%2==0).mkString(",").foreach(print)
    println("")
  }
}


object obj15{
  def main(args: Array[String]): Unit = {
    val l = List(2,4,6,8,1,3,5,7, 2,2,2,2)
    println(l.splitAt(3))
    println(l.span(_%2==0))
  }
}

object obj16{
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3)
    l.padTo(10, 0).mkString(",").foreach(print)
    println("")
  }
}

object obj17{
  def main(args: Array[String]): Unit = {
    val l1 = List(1,2,3)
    val l2 = List("a","b","c")
    l1.zip(l2).mkString(",").foreach(print)
    println("")
    l2.zipWithIndex.mkString(",").foreach(print)
    println("")
  }
}
