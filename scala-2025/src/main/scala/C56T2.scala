package AAA {
  object 类型561 {
    def main(args: Array[String]): Unit = {
      //    var a = "123"
      //    a = null
      //    var b = 123
      //    b = null

      def f1() = {
        println(123)
      }

      f1

      var f2 = {
        println(123)
      }
      f2

      var f3 = println(123)
      f3

      var a = 123
      a

      var b = {
        123
      }
      println(a + b)

    }
  }
}

package BBB {
  package C {
    object 类型562 {
      def main(args: Array[String]): Unit = {
        var a = 1

        def f1 = {
          println(a + 1)
          a = a + 1
          a + 2
        }

        var f2 = {
          a + 3
        }
        a = 10
        println(a)
        println(f1)
        println(a)
        println(f2)


      }
    }
  }

  package D {
    object 类型563 {
      def main(args: Array[String]): Unit = {
        def f1() = {
          123
        }

        def f2 = {
          "abc"
        }

        var a = 1
        var b = 2

        def f3 = {
          if (a > b) {
            123
          } else {
            "abc"
          }
        }

        def f4: Nothing = {
          throw new Exception()
        }

      }
    }
  }

  object 类型564 {
    def main(args: Array[String]): Unit = {
      var a = 1L
      var b: Int = a.toInt
      var c: Int = b
    }
  }
}

object 运算符56 {
  def main(args: Array[String]): Unit = {
    var a = "xxx"
    var b = "xxx"
    println(a==b)
    var c = "xx"+"x"
    println(a==c)
    var d = new String("xxx")
    println(a==d)
    println(a>c)
    var e = "20"
    var f = "200"
    println((e<f)&&(a==c))

    var x =1
    x = x+1
    println(x)
    x += 1
    println(x)
//    x++

  }
}

object 判断56 {
  def main(args: Array[String]): Unit = {
    var a = 1
    var b = 2
    if(a>b){
      println("a")
    } else if(a>0){
      println("a>0")
    } else{
      println("b")
    }

    if(a>b)
      println("a")
    else if(a>0)
      println("a>0")
    else
      println("b")

    if(a>b) println("a") else println("b")

    var result = if(a>b) "a" else "b"
    println(result)

    def res = if(a>b) "a" else "a"
  }
}

object 循环56 {
  def main(args: Array[String]): Unit = {
    for(i <- 1 to 10){
      print(i)
    }
    println()
    for(i <- 1 until 10){
      print(i)
    }
    println()
    for(i <- 1.to(10)){
      print(i)
    }
    println()
    for(i <- 1.until(10)){
      print(i)
    }
    println()
    for(i <- Range(1,10,2)){
      print(i)
    }
    println()
    for(i <- Range(1,18,2)){
      println(" "*((18-i)/2)+"*"*i)
    }
    println()
    for(i <- Range(1,18) if i %2 ==1){
      println(" "*((18-i)/2)+"*"*i)
    }
    println()
    for (i <- Range(1, 18, 2); j = (18 - i) / 2) {
      println(" " * j + "*" * i)
    }
    for {i <- Range(1, 18, 2)
         j = (18 - i) / 2} {
      println(" " * j + "*" * i)
    }

    var res1 = for(i<- 1 to 10){
      print(i)
    }
    println(res1)
    var res2 = for(i<-1 to 10 if i>1 && i<7 && i%2==0 ) yield i
    println(res2.toList)


  }
}