package c12.l04

import scala.collection.convert.Wrappers.MutableBufferWrapper
import scala.collection.mutable
import scala.io.Source

object a1 {
  def main(args: Array[String]): Unit = {
    var s1 = Set(1,2,3)
    println(s1)
    var s2 = Set(1,2,3,4,3,2,1)
    println(s2)

    var s3 = s1 ++ s2
    println(s3)


    var s4 = s3 - 3
    println(s4)
    s4.foreach(println(_))

    println(s4.toList)

    var s5 = mutable.Set(1,2,3)
    println(s5)
    s5.add(4)
    println(s5)


  }
}

object a2 {
  def main(args: Array[String]): Unit = {
    var m1 = Map("a"->1,"b"->2,"c"->3)
    println(m1)
    println(m1("a"))
//    println(m1("d"))
    println(m1.getOrElse("d",10))

    var m2 = m1 + ("d"->4)
    println(m2)

    m2 -= "c"
    println(m2)

    var m3 =  m2.updated("a",10)
    println(m2, m3)

    var m4 = mutable.Map("a"->1,"b"->2,"c"->3)
    println(m4)
    m4.put("d", 4)
    println(m4)
  }
}

object a3 {
  def main(args: Array[String]): Unit = {
    var t1 = (1,2,3)
    println(t1)
    println(t1._1, t1._2, t1._3)
    var t2 = (1,2,3,4)
    println(t2._1)
    var t22 = (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22)
    println(t22._21)
//    println((1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23))
    for(i<-t22.productIterator){
      println(i)
    }

    println(1,2,3)

    var l1 = List(1,2,3)
    var l2 = List('a','b','c')
    println(l2.zip(l1))

  }
}


object a4 {
  def main(args: Array[String]): Unit = {
    var kq = 10
    var score = "b"
    score match {
      case "a" => println("nice!!!")
      case "b" => {
        if(kq>8) println("nice!!")
        else println("nice")
      }
      case "c" => println("good")
      case _ => println("无法识别")
    }
  }
}


object a5 {
  def main(args: Array[String]): Unit = {
    var score = "98"
    var result = score match {
      case "a" => "优秀"
      case "b" => "良好"
      case "c" => "及格"
      case aaa => if(aaa.toInt>90) "优秀" else "良好"
    }
    println(result)
  }
}

object a6 {
  def main(args: Array[String]): Unit = {
    // 类型匹配
    val a = 8
    val obj = if (a == 1) 1
    else if (a == 2) "2"
    else if (a == 3) BigInt(3)
    else if (a == 4) Map("104" -> 4)
    else if (a == 5) Map(5 -> "105")
    else if (a == 6) Array(1, 2, 3, 4, 5, 6)
    else if (a == 7) Array("77", 7)
    else if (a == 8) Array("88")

    val result = obj match {
      case a: Int => a
      case b: Map[String, Int] => "对象是一个Map[String,Int]类型的映射"
      case c: Map[Int, String] => "对象是一个Map[Int,String]类型的映射"
      case d: Array[String] => "对象是一个字符串数组"
      case e: Array[Int] => "对象是一个整数数组"
      case g: Array[Object] => "对象是一个Object数组"
      case f: BigInt => Int.MaxValue
      case _ => "啥也不是"
    }
    println(obj, result)

  }
}

object a7 {
  def main(args: Array[String]): Unit = {
    def greeting(arr: Array[Int]) {
      arr match {
        case Array(0) => println("只匹配元素为 0 的数组")
        case Array(x, y, z) => println(s"匹配三个元素的数组，并赋值：x=$x,y=$y,z=$z")
        case Array(0, _*) => println("匹配以 0 开头的数组，个数可以多个")
        case _ => println("什么都没有匹配上")
      }
    }
    greeting(Array(2,5,7))
    greeting(Array(0,4,6))
    greeting(Array(3))

  }
}

object a8 {
  def main(args: Array[String]): Unit = {
    // 匹配列表
    for (list <- Array(
      List(0),
      List(1, 3),
      List(2, 3, 0),
      List(1, 0, 0))
         ) {
      val result = list match {
        case 0 :: Nil => "只有包含元素0的列表" //
        case x :: y :: Nil => "包含两个元素，并赋值为x和y："+x + " " + y // List(x,y)
        case 2 :: tail => "以 2 开头" // List(2,_*)
        case _ => "什么都不是"
      }
      println(result)
    }
  }
}


object a9 {
  def main(args: Array[String]): Unit = {
    // 匹配元组
    for (pair <- Array(
      (0, 1),
      (1, 0),
      (2, 1),
      (1,0,2)
    )) {
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

object a10 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,"a")
    println(l1.collect{case e:Int=>e}.filter(_%2==1))
  }
}

object a11 {
  def main(args: Array[String]): Unit = {
    def f()={
      println("a")
      "b"
    }
    lazy val x = f()
    println("c")
    println(x)
    println("d")
    println(x)
  }
}


object a12 {
  def main(args: Array[String]): Unit = {
    var lines = Source.fromFile("input/books/the_old_man_and_the_sea.txt")
      .getLines().toList
    lines
      .map(_.trim.split(" ").toList)
      .filter(_.length>1)
      .flatten
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .reverse
      .take(10)
      .foreach(println(_))
  }
}

object a13 {
  def main(args: Array[String]): Unit = {
    // map
    val nums = List(1, 2, 3)
    val square = (x: Int) => x * x
    val squareNums1 = nums.map(num => num * num) //List(1,4,9)
    val squareNums2 = nums.map(math.pow(_, 2)) //List(1,4,9)
    val squareNums3 = nums.map(square) //List(1,4,9)
    println(squareNums1, squareNums2,squareNums3)
  }
}

object a14 {
  def main(args: Array[String]): Unit = {
    // flatmap
    val text = List("A,B,C", "D,E,F")
    val textMapped = text.map(_.split(",").toList) // List(List("A","B","C"),List("D","E","F"))
    val textFlattened = textMapped.flatten // List("A","B","C","D","E","F")
    val textFlatMapped = text.flatMap(_.split(",").toList) // List("A","B","C","D","E","F")
    println(textMapped, textFlatMapped, textFlattened)
  }
}

object a15 {
  def main(args: Array[String]): Unit = {
    // reduce
    val nums = List(1, 2, 3)
    val sum1 = nums.reduce((a, b) => {println(a+b);a + b}) //6
    val sum2 = nums.reduce(_ + _) //6
    val sum3 = nums.sum //6
    println(sum1,sum2,sum3)
  }
}

object a16 {
  def main(args: Array[String]): Unit = {
    { // fold
      val nums = List(2, 3, 4)
      val sum = nums.fold(1)(_ + _) // = 1+2+3+4 = 9
      val nums2 = List(2.0, 3.0)
      val result1 = nums2.foldLeft(4.0)(math.pow) // = pow(pow(4.0, 2.0),3.0) = 4096
      val result2 = nums2.foldRight(1.0)(math.pow) // = pow(2.0,pow(3.0 ,1.0)) = 8.0
    }

  }
}















