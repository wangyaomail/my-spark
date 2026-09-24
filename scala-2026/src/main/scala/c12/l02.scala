package c12

import scala.util.control.Breaks

object l0201 {
  def main(args: Array[String]): Unit = {
    var flag = true
    if(flag){
      println("true")
    } else{
      println("false")
    }
    var a = 1
    var b = 2
    var x = if(a>b) true else false
    var c = "123"

    var d = if(x){
      "A"
    } else{
      123
    }
    println(c)

  }
}


object l0202 {
  def main(args: Array[String]): Unit = {
    val flag = true
    if(flag)
      println("a")
    else
      println("b")
  }
}

object l0203 {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      println(i);
    }
    for (i <- 1 until 10) {
      println(i);
    }
    var a = List(1, 2, 3)
    for (i <- a) {
      println(i)
    }
    var b = (1 to 10).toList
    println(b)
    var c = List.fill(10)(0)
    println(c)
    var d = List.fill(10)(math.random().toString.substring(0, 4).toDouble)
    println(d)

    for (i <- Range(1, 10, 2)) {
      print(i, "")
    }

    for (i <- Range(1, 18, 2)) {
      println(" " * ((18 - i) / 2) + "*" * i)
    }
    for (i <- Range(1, 10) if (i % 2 == 0)) {
      print(i, "")
    }

    var e = for (i <- for (j <- 2 to 4) yield j) yield i + 2

    println(e, "")

    Breaks.breakable {
      for (i <- 1 to 10) {
        println(i)
        if (i == 5) {
          Breaks.break()
        }
      }
    }
  }

}


object l0204 {
  def main(args: Array[String]): Unit = {
    def f1()={
      println("f1")
    }
    f1
    f1
    def f2 = {
      println("f2")
    }
    f2
    f2
    var f3 = {
      println("f3")
    }
//    f3
//    f3
    var f4 = ()=>{
      println("f4")
    }
    f4()
    f4()
  }
}

object l0205 {
  def main(args: Array[String]): Unit = {
    var x = 2%2==0
    def f1:Any={
      if(x) "f1" else 123
    }
    println(f1)
    def f2(x:Int)={
      x*2
    }
    println(f2(4))
    def f3(x:Int, y:Int)={
      x*y
    }
    println(f3(2,3))
    println(f3(y=5,x=6))
    def f4(x:Int=1,y:Int=2)={
      ("x=",x,"y=",y)
    }
    println(f4())
    println(f4(2,3))
    println(f4(4))
    println(f4(y=5))
    def f5(x:Int,y:Int=2) = {
      ("x=",x,"y=",y)
    }
    println(f5(1))
    println(f5(1,3))
    def f6(x:Int=1,y:Int) = {
      ("x=",x,"y=",y)
    }
    println(f6(y=2))
    println(f6(2,3))
    def f7(){
      println("mei fan hui zhi")
      "f7"
    }
    println(f7())

    def f8(a:Any*)={
      (a.toList, a.toList.size)
    }
    println(f8(1,2,3,4,"a",'b',true))
  }
}

object l0206 {
  def main(args: Array[String]): Unit = {
    val num = 10
    var farr = new Array[Int](num+1)
    def f1(a: Int): Int = {
      if(farr(a)==0) {
        if (a == 1 || a == 2) {
          farr(a) = 1
        } else {
          farr(a) = f1(a - 1) + f1(a - 2)
        }
      }
      farr(a)
    }
    f1(10)
    farr.foreach(println)
  }
}


object l0207{
  var b = 2
  def main(args: Array[String]): Unit = {
    var a = 1
    def f1(x:Int)={
      a+=1
      b+=1
      (x,a,b)
    }
    println(f1(2))
    println(f1(2))
    println(f1(2))
    def f2(x:Int)={
      def f3(y:Int)={
        def f4(z:Int)={
          x+y+z
        }
        f4 _
      }
      f3 _
    }
    println(f2(1)(2)(3))
    def f5(x:Int) = {
      x+1
    }
    def f6(x:Int=>Int)={
      2+x(2)
    }
    def f7(x:Int) = {
      x*2
    }
    def f8(x:Int) = {
      x/2
    }
    println(f6(f5))
    println(f6(f7))
    println(f6(f8))

    def mul(x:Int)(y:Int)= x*y

    def mul2(x:Int) = mul(2)(x)

    println(mul(3)(5))
    println(mul2(3))
  }
}