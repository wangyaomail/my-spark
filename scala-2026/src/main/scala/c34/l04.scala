package c34.l04

import scala.collection.mutable
import scala.io.Source

object a1 {
 def main(args: Array[String]): Unit = {
   var s1 = Set(5,3,2,6,1)
   println(s1(3))
   println(s1(4))
   var s2 = Set('a',"b",true)
   var s3 = s1++s2
   println(s3)
   s3 -= 'a'
   println(s3)
   var s4 = mutable.Set(1,2,3)
   println(s4)
   s4.add(4)
   println(s4)
 }
}

object a2 {
  def main(args: Array[String]): Unit = {
    var m1 = Map('a'->1,'b'->2,'c'->3)
    println(m1)
    println(m1('b'))
    m1 += ('d'->4)
    println(m1)
    m1 -= 'c'
    println(m1)
    m1 = m1.updated('b',9)
    println(m1)
    println(m1.getOrElse('b',100))
    println(m1.getOrElse('c',100))
    var m2 = mutable.Map('a'->'b','c'->'d')
    println(m2)
    m2.put('e','f')
    println(m2)
  }
}

object a3 {
  def main(args: Array[String]): Unit = {
    var t1 = (1,2,3)
    println(t1)
    println(t1._1, t1._2, t1._3)
    var t2 = (1,2,3,4)
    println(t2._1)
    var t3 = (1,2,3,4,5)
    println(t3._1)
    var t22 = (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22)
    println(t22._1)
//    var t23 = (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23)
//    println(t23._1)
    for(i<-t22.productIterator){
      println(i)
    }
    var l1 = List(1,2,3,4)
    var l2 = List(5,6,7,8)
    var l3 = l1.zip(l2)
    println(l3)
    l3.map(x=>println(x._1,x._2))

  }
}

object a4 {
  def main(args: Array[String]): Unit = {
    var kq = 10
    var score = "c"
    score match {
      case "a" => println("nice")
      case "b" => println("good")
//      case "c" => println("cbd")
      case "c" => {
        if(kq>5) println("pass")
        else println("block")
      }
      case _ => println("keep going")
      case _ => println("brs")
    }

    def getMark(score: String): Unit = {
      score match {
        case "A" => println("优秀")
        case "B" => println("良好")
        case "C" => println("及格")
        case x => println(s"您当前的评分为：${x}，因此不及格")
      }
    }
    getMark("B")
    getMark("98")

  }
}

object a5 {
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
      case f: BigInt => Int.MaxValue
      case _ => "啥也不是"
    }
    println(result)
  }
}

object a6 {
  def main(args: Array[String]): Unit = {
    def greeting(arr: Array[Int]) {
      arr match {
//        case Array(_*) => println("AAA")
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

object a7 {
  def main(args: Array[String]): Unit = {
    // 匹配列表
    for (list <- Array(List(0), List(1, 3), List(2, 3, 0), List(1, 0, 0))) {
      val result = list match {
        case 0 :: List() => "只有包含元素0的列表" //
        case x :: y :: Nil => "包含两个元素，并赋值为x和y："+x + " " + y
        case 2 :: tail => "以 2 开头"
        case _ => "什么都不是"
      }
      println(result)
    }

  }
}

object a8 {
  def main(args: Array[String]): Unit = {
    // 匹配元组
    for (pair <- Array((0, 1), (1, 0), (2, 1),(1,0,2))) {
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

object a9 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,"a")
    println(l1.collect{case e:Int=>e}.filter(_%2==1))
    //将以下list中的整数型的元素分别+10
    val list = List(2,4,6,7,"哈哈")
    val res = list.collect{case e:Int => e+10}
    println(res)
  }
}

object a10 {
  def main(args: Array[String]): Unit = {
    def f()={
      println("a")
      "b"
    }
    lazy val name = f();
    println("c")
    println(name)
    println("d")
    println(name)
  }
}

object a11 {
  def main(args: Array[String]): Unit = {
    var src = Source.fromFile("input/books/the_old_man_and_the_sea.txt")
      .getLines()
    src
      .map(_.trim.toLowerCase.split(" ").toList)
      .filter(x=>x.length>1)
      .flatten.map((_,1)).toList
      .groupBy(_._1)
      .map(x=>(x._1,x._2.length))
      .toList
      .sortBy(-_._2)//.reverse
      .take(10)
      .foreach(println(_))
  }
}

object a12 {
  def main(args: Array[String]): Unit = {
    // map
    val nums = List(1, 2, 3)
    val square = (x: Int) => x * x
    val squareNums1 = nums.map(num => num * num) //List(1,4,9)
    val squareNums2 = nums.map(math.pow(_, 2)) //List(1,4,9)
    val squareNums3 = nums.map(square) //List(1,4,9)

    println(squareNums1,squareNums2,squareNums3)
  }
}

object a13 {
  def main(args: Array[String]): Unit = {
    // flatmap
    val text = List("A,B,C", "D,E,F")
    val textMapped = text.map(_.split(",").toList) // List(List("A","B","C"),List("D","E","F"))
    val textFlattened = textMapped.flatten // List("A","B","C","D","E","F")
    val textFlatMapped = text.flatMap(_.split(",").toList) // List("A","B","C","D","E","F")
    println(textMapped,textFlatMapped, textFlattened)
  }
}

object a14 {
  def main(args: Array[String]): Unit = {
    // reduce
    val nums = List(1, 2, 3)
    val sum1 = nums.reduce((a, b) => a + b) //6
    val sum2 = nums.reduce(_ + _) //6
    val sum3 = nums.sum //6
    println(sum1, sum2, sum3)
  }
}


object a15 {
  def main(args: Array[String]): Unit = {
    val nums = List( 3, -2, +3)
    println(nums.map(_+20))
    (20::nums).reduce((x,y)=>{println(x+y);x+y})
    println(nums.fold(20)(_+_))
  }
}

object a16 {
  def main(args: Array[String]): Unit = {
    // sortby
    val nums = List(5,8,3,5,7)
    val sorted = nums.sorted //List(1,2,3,4)
    val users = List(("HomeWay", 25), ("XSDYM", 23))
    val sortedByAge = users.sortBy {x=>x._2} //List(("XSDYM",23),("HomeWay",25))
    val sortedWith = users.sortWith { case (user1, user2) => user1._2 < user2._2 } //List(("XSDYM",23),("HomeWay",25))
    println(sorted,sortedWith,sortedByAge)
  }
}

object a17 {
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4,5,6,7,8)
    println(l.filter(_%3==0))
    println(l.count(_%2==0), l.sum, l.size, l.length)
  }
}

object a18 {
  def main(args: Array[String]): Unit = {
    { // diff
      val nums1 = List(1, 2, 3)
      val nums2 = List(2, 3, 4)
      val diff1 = nums1 diff nums2 // List(1)
      val diff2 = nums2.diff(nums1) // List(4)
      val union1 = nums1 union nums2 // List(1,2,3,2,3,4)
      val union2 = nums2 ++ nums1 // List(2,3,4,1,2,3)
      val intersection = nums1 intersect nums2 //List(2,3)
      println(diff1,diff2,union1,union2,intersection)
      println(union1.distinct)
      println(union1.map((_,1)).groupBy(_._1).map(_._1))
    }
  }
}

object a19 {
  def main(args: Array[String]): Unit = {
    // groupby
    val data = List(
      ("HomeWay", "Male"),
      ("XSDYM", "Female"),
      ("Mr.Wang", "Male"))
    val group1 = data.groupBy(_._2)
    // = Map("Male" -> List(("HomeWay","Male"),("Mr.Wang","Male")),"Female" -> List(("XSDYM","Femail")))
    val group2 = data.groupBy { case (name, sex) => sex }
    // = Map("Male" -> List(("HomeWay","Male"),("Mr.Wang","Male")),"Female" -> List(("XSDYM","Femail")))
    val fixSizeGroup = data.grouped(2).toList
    // = Map("Male" -> List(("HomeWay","Male"),("XSDYM","Femail")),"Female" -> List(("Mr.Wang","Male")))
    println(group1,group2,fixSizeGroup)
  }
}

object a20 {
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4,5)
    println(l.scan(0)(_+_))
    println(l.reduce(_+_))
  }
}

object a21 {
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4,5)
    println(l.take(3), l.takeRight(2))
    println(l.takeWhile(_<3))
  }
}

object a22 {
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4,5)
    println(l.drop(3), l.dropRight(2))
    println(l.dropWhile(_<3))
  }
}

object a23 {
  def main(args: Array[String]): Unit = {
    // span
    val nums = List(1, 1, 1, 2, 3, 2, 1)
    val (prefix1, suffix1) = nums.span(_ == 1) // prefix = List(1,1,1), suffix = List(2,3,2,1)
    val (prefix2, suffix2) = nums.splitAt(3) // prefix = List(1,1,1), suffix = List(2,3,2,1)
    val (prefix3, suffix3) = nums.partition(_ == 1) // prefix = List(1,1,1), suffix = List(2,3,2)
    println((prefix1, suffix1))
    println((prefix2, suffix2))
    println((prefix3, suffix3))
    println(nums.padTo(10,99))
  }
}

object a24 {
  def main(args: Array[String]): Unit = {
    // zip
    val alphabet = List("A", "B", "C")
    val nums = List(1, 2)
    val zipped = alphabet zip nums // List(("A",1),("B",2))
    val zipped2 = alphabet.zip(nums)
    val zippedAll = alphabet.zipAll(nums, "*", -1) // List(("A",1),("B",2),("C",-1))
    val zippedIndex = alphabet.zipWithIndex // List(("A",0),("B",1),("C",3))
    val (list1, list2) = zipped.unzip // list1 = List("A","B"), list2 = List(1,2)
    val (l1, l2, l3) = List((1, "one", '1'), (2, "two", '2'), (3, "three", '3')).unzip3
    // l1=List(1,2,3),l2=List("one","two","three"),l3=List('1','2','3')
    println(zipped)
    println(zippedAll)
    println(zippedIndex)
    println(list1, list2)
    println(l1, l2, l3)
  }
}

object a25 {
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4,5,6)
    println(l.slice(2,4))
  }
}

object a26 {
  def main(args: Array[String]): Unit = {
    var l = List(1,2,3,4,5,6)
    l = l.updated(3,99)
    println(l)
  }
}






