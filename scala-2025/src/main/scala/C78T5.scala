package c78

import scala.io.Source

object obj1 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5) // Linkedlist
    var a1 = Array(1,2,3,4,5) //arr
    println(l1)
    println(a1)

    var l2 = Nil
    var l3 = List(1,2,3,"a","b")
    var l4 = List(4,5,6)
    var l5 = l3 :: l4
    var l6 = 1:: 2 :: 3 :: l4
    println(l5,l6)
    var l7 = l6 +: l5
    println(l7)

    var l8 = l3 ::: l4
    println(l8)

    var l9 = List.concat(l3, l4)
    println(l9)
    var l10 = List.fill(5)("abc")
    println(l10)
    var l11 = new Array[Double](10).toList.map(x=>1.0/10)
    var l12 = List.fill(5)(1.0/10)

    println(l11, l12)

    var l13 = List(1,3,2,5,4)
    println(l13)
    var l14 = l13.reverse
    println(l14)
    var l15 = l13.sorted
    println(l15)

    l15.foreach(println)
    for(x<-l15){
      println(x)
    }

    println(l15.mkString(","))


  }
}

object obj2 {
  def main(args: Array[String]): Unit = {
    var s1 = Set(1,2,3,4,5)
    println(s1)
    var s2 = Set(3,5,2,1,4)
    println(s2)

    var s3 = s1 + (6,7,8)
    println(s3)
    var s4 = s1 + (4,5,6)
    println(s4)

    s4.foreach(println)
    for(x<- s4){
      println(x)
    }
    println(s4.mkString(","))
  }
}

object obj3 {
  def main(args: Array[String]): Unit = {
    var m1 = Map(1->"a", 2->"b", 3->"c")
    println(m1)
    println(m1.mkString(",")) // 1:a,2:b,3:c
    var sb = ""
    m1.foreach(x=>sb+=x._1+":"+x._2+",")
    println(sb.substring(0,sb.length-1))
    println(m1.map(x=>x._1+":"+x._2)
      .mkString(","))

    println(m1.get(2))
//    m1 += (4->"d")
//    println(m1)
    var m2 = m1 + (4->"d")
    println(m2)
    println(m1)




  }

}


object obj4 {
  def main(args: Array[String]): Unit = {
    var y1 = (1,2,3,4,5)
    println(y1._1, y1._2)
    for(x<-y1.productIterator){
      println(x)
    }
    var m1 = Map(1->"a", 2->"b", 3->"c")
    var m2 = Map((1,"a"),(2,"b"),(3,"c"))
    println(m1,m2)
  }
}

object obj5 {
  def main(args: Array[String]): Unit = {
    var score = "A"
    var age = 17
    score match {
      case "A" => {
        age match {
          case 15 => println("中学生")
          case _ if age>16 =>println("高二或大学")
          case _ if age<18 => println("未成年")
          case 17 => println("高二")
        }
      }
      case "B" => println("良好")
      case _ => println("不好")
    }

  }
}

object obj6 {
  def main(args: Array[String]): Unit = {
    // 类型匹配
    val a = 1
    val obj = if (a == 1) BigInt(10)
    else if (a == 8) Array("88")

    val result = obj match {
      case a: Int => a
      case b: Map[String, Int] => "对象是一个Map[String,Int]类型的映射"
      case c: Map[Int, String] => "对象是一个Map[Int,String]类型的映射"
      case d: Array[String] => "对象是一个字符串数组"
      case e: Array[Int] => "对象是一个整数数组"
      case f: BigInt => Int.MaxValue
      case _ => "啥也不是"
    }
    println(result)

  }
}

object obj7 {
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
    greeting(Array(0,4,6))
    greeting(Array(3))
  }
}

object obj8 {
  def main(args: Array[String]): Unit = {
    //样例类
    //抽象Person类
    abstract class Person
    //样例类Student
    case class Student(name: String, age: Int, stuNo: String) extends Person
    //样例类Teacher
    case class Teacher(name: String, age: Int, teaNo: String) extends Person
    //样例类 Nobody
    case class Nobody(name: String) extends Person
    //case class会自动生成apply方法，创建对象时无需用new
    val p: Person = Student("lisi", 20, "101")
    //match case模式匹配
    p match {
      case Student(name, age, stuNo) => println(s"学生：$name, $age, $stuNo")
      case Teacher(name, age, teaNo) => println(s"老师：$name, $age, $teaNo")
      case Nobody(name) => println(s"其他人：$name")
    }
  }
}

object obj9 {
  def main(args: Array[String]): Unit = {
    val list = List(2,4,6,7,"哈哈")
    val res = list.collect{case e:Int => e+10}
    println(res)
  }
}

object obj10 {
  def main(args: Array[String]): Unit = {
    def f()={
      println("a")
      "b"
    }
    lazy val name = f();
    println("c")
    println(name)
  }
}

object obj11 {
  def main(args: Array[String]): Unit = {
    val source = Source
      .fromFile("input/books/the_old_man_and_the_sea.txt", "UTF-8")
      .getLines()
      .toArray
//    source.foreach(println)
    source.map(x=>x.trim.split(" ").toList)
      .flatten
      .map(x=>(x,1))
      .groupBy(x=>x._1)
//      .map(x=>(x._1,x._2.toList))
      .map(x=>(x._1,x._2.map(_._2).sum))
      .toList
      .sortBy(x=>x._2)
      .reverse
//      .foreach(println)
    source.map(_.trim.split(" ").toList)
      .flatten
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2)
      .reverse
      .foreach(println)
  }
}

object obj12 {
  def main(args: Array[String]): Unit = {
    // map
    val nums = List(1, 2, 3)
    val square = (x: Int) => x * x
    val squareNums1 = nums.map(num => num * num) //List(1,4,9)
    val squareNums2 = nums.map(math.pow(_, 2)) //List(1,4,9)
    val squareNums3 = nums.map(square) //List(1,4,9)
    println(squareNums1,squareNums2,squareNums3)

    // flatmap
    val text = List("A,B,C", "D,E,F")
    val textMapped = text.map(_.split(",").toList) // List(List("A","B","C"),List("D","E","F"))
    val textFlattened = textMapped.flatten // List("A","B","C","D","E","F")
    val textFlatMapped = text.flatMap(_.split(",").toList) // List("A","B","C","D","E","F")
    println(textMapped)
    println(textFlattened)
    println(textFlatMapped)
  }
}


