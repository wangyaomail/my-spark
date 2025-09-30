package c56
package t5

import scala.io.Source

object obj1 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    var a1 = Array(1,2,3,4,5)
    println(l1)
    println(a1)

    var a2 = new Array[Int](10)
    var l2 = Nil
    var l3 = List()
    println(l2,l3)
    var l4 = 6 :: 7 :: 8 :: l1
    println(l4)
    var l5 = List(6,7,8)
    var l6 = l5 :: l1
    println(l6)
    var l7 = l5 ::: l1
    println(l7)
    var l8 = List.concat(l5,l1)
    println(l8)
    var l9 = l1 :+ l5
    println(l9)
    var l10 = List.fill(10)("a")
    println(l10)
    var l11 = List.fill(10)(1.0/10)
    println(l11)

    var l12 = List(1,3,2,4,5)
    var l13 = l12.reverse
    println(l13)
    var l14 = l12.sorted
    println(l14.reverse)

    l14.foreach(println)
    for(x<-l14){
      println(x)
    }

    println(l14.mkString(","))
  }
}

object obj2 {
  def main(args: Array[String]): Unit = {
    var s1 = Set(1,2,3,4,5)
    println(s1)
    println(s1.toList.sorted)
    s1.foreach(println)
    for(x<-s1){
      println(x)
    }
    s1 += 10
    println(s1)
    var s2 = s1 + 11
    println(s1,s2)

    println(s2.mkString(","))
  }
}

object obj3 {
  def main(args: Array[String]): Unit = {
    var m1 = Map("a"->1, "b"->2)
    var m2 = Map(("a",1),("b",2))
    println(m1)
    println(m2)
    var m3 = m1 + (("c",3))
    println(m1, m3)
    m3.foreach(println)
    for(x<-m3){
      println(x._1, x._2)
    }

    println(m3.mkString(",")) // a:1,b:2,c:3
    var m3str = m3.map(x=>x._1+":"+x._2)
                  .mkString(",")
    println(m3str)

    println(m3.get("a"))
    println(m3.get("d"))
    println(m3.getOrElse("d",4))

  }
}

object obj4 {
  def main(args: Array[String]): Unit = {
    var y1 = (1,2,3)
    println(y1._1)
    y1.productIterator.foreach(println)

  }
}


object obj5 {
  def main(args: Array[String]): Unit = {
    var s = "A"
    s match {
      case _ => println("else")
      case _ => println("else!!!")
      case "A" => println("nice")
      case "A" => println("nice!!!")
      case "B" => println("good")
    }

    var result = s match {
      case "A" => {
        println("nice A")
        90
      }
      case "B" => 80
      case _ => "<80"
    }
    println(result)

    var score = 81
    var result2 = score match {
      case _ if score>80  => {
        score match {
          case _ if score>50 =>{
            println("score > 50")
          }
        }
        println("score > 80")
        score
      }
      case 79 => 80
      case _ => "<80"
    }
    println(result2)
  }
}

object obj6 {
  def main(args: Array[String]): Unit = {
    var s = "A"
    s match {
      case a => println("else",a)
      case _ => println("else!!!")
      case "A" => println("nice")
      case "A" => println("nice!!!")
      case "B" => println("good")
    }

    // 类型匹配
//    val a:Any = 1
//    val obj = if (a == 1) 1
//    else if (a == 2) "2"
//    else if (a == 3) BigInt(3)
//    else if (a == 4) Map("104" -> 4)
//    else if (a == 5) Map(5 -> "105")
//    else if (a == 6) Array(1, 2, 3, 4, 5, 6)
//    else if (a == 7) Array("77", 7)
//    else if (a == 8) Array("88")

    val a:Any = 1.0
    val result = a match {
      case a: Int => a
      case b: Map[String, Int] => "对象是一个Map[String,Int]类型的映射"
      case c: Map[Int, String] => "对象是一个Map[Int,String]类型的映射"
      case d: Array[String] => "x"
      case e: Array[Int] => "y"
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
    greeting(Array(2,5))
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

    val list = List(2,4,6,7,"哈哈")
    val res = list.collect{case e:Int => e+10}
    println(res)
  }
}

object obj9 {
  def main(args: Array[String]): Unit = {
    def f()={
      println("a")
      "b"
    }
    lazy val name = f();
    println("xxx")
    var c = name +""
    println("c")
    println(name)
    println(c)
  }
}

object obj10 {
  def main(args: Array[String]): Unit = {
    var s = Source
      .fromFile("input/books/the_old_man_and_the_sea.txt","utf8")
      .getLines()
      .toList
//    println(s)
    s.map(x=>x.trim.split(" ").toList)
      .flatten
      .map(x=>(x,1))
      .groupBy(x=>x._1)
//      .map(x=>(x._1,x._2.size))
      .map(x=>(x._1,x._2.map(y=>y._2).sum))
      .toList
      .sortBy(x=>x._2 * -1)
//      .foreach(println)


    var result = s.map(_.trim.split(" ").toList)
      .flatten
      .map((_,1))
      .groupBy(_._1)
      .map(x=>(x._1,x._2.size))
      .toList
      .sortBy(_._2 * -1)
//      .foreach(println)
      .map(x=>x._1+":"+x._2)
      .mkString(",")
    println(result)
  }
}