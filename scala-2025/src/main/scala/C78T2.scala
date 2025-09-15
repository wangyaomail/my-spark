package a {
  object Scala类型测试 {
    def main(args: Array[String]): Unit = {
      var a: String = "abc"
      a = null
      var b: Int = 12

      //    b = null
      def f1(): Unit = {
        println("123")
      }

      f1

      def f2(): Int = {
        println("f2")
        123
      }

      println(f2)
      var d = 789

      def f3(): Unit = {
        println("f3 run")
        "abc"
      }

      f3
      println(d) //+f3)

      def f4(): Nothing = {
        throw new Exception()
      }

      f4


    }
  }
}

package b {

  object Scala类型测试2 {
    var a = 10

    def main(args: Array[String]): Unit = {

      def f1 = {
        var a = 20
        println(a + 10)

        a = a + 1
        a + 2
      }

      println(a)
      println(f1)
      println(a)

      var b = 10L
      var c: Int = b.toInt
      var d: Long = c
      println(b, c, d)

    }
  }
}

import scala.Tuple22
import scala.util.control.Breaks
object 元组ceshi {
  def main(args: Array[String]): Unit = {
    println(1,2,3,4)
    println(1,2,3,4,5)
    println(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22)
  }
}

object 符号 {
  def main(args: Array[String]): Unit = {
    var aa = "xxx"
    var bb = "xxx"
    println(aa==bb)
    var cc = "xx"+"x"
    println(cc == aa)
    var dd = new String("xxx")
    println(dd == aa)
    var ee = "11"
    var ff = "ab"
    var gg = "102"
    println((ee>ff)||(ee>gg))
    println((ee>ff)&&(ee>gg))

    var hh = 1
    hh += 1
    println(hh)



  }
}

object ifelse {
  def main(args: Array[String]): Unit = {
    var x = true
    if(x){
      println("true")
    } else{
      println("false")
    }

    val a = 4
    val b = 1
    if(a > 2){
      if(b > 2)
        println(a + b)
//        println(a + b)
      else
        println("a 是 "+ a)
    }

    println( if(a>b) a else b)

    def f1 = if(a>b) a else b
    println(f1)

    def f2 = if(a>b) "1" else 2
    println(f2)

  }
}

object 循环控制 {
  def main(args: Array[String]): Unit = {
    for(i <- 1 to 10){
      println(i)
    }
    for(j <- 'a' until 'z'){
      println(j)
    }

    println(1 to 10)
    println( 1.to(10))

    for(i <- Range(1,18,2)){
      println(" "*((18-i)/2)+"*"*i)
    }

    for(i<- 1 to 10 if i%2 == 0){
      println(s"$i")
    }

    for (i <- Range(1, 18, 2); j = (18 - i) / 2) {
      println(" " * j + "*" * i)
    }
    for {i <- Range(1, 18, 2)
         j = (18 - i) / 2} {
      println(" " * j + "*" * i)
    }

    val f1 = {
      println("123")
    }
    f1

    def r1 = for(i<- 1 to 10){
      print(i)
    }
    r1
    var r2 = for(i <- 1 to 10) yield i
    println(r2.toList)

  }
}


object 中断 {
  def main(args: Array[String]): Unit = {
    Breaks.breakable {
      for (i <- 1 to 10) {
        print(i)
        if (i % 5 == 0) {
          Breaks.break()
        }
      }
    }
  }
}
