package c56

import scala.util.control.Breaks

object l0201 {
  def main(args: Array[String]): Unit = {
    var a = true
    var b = false
    if(a){
      println("a")
    } else if (b){
      println("b")
    } else{
      println("c")
    }
    var x = if(a) {
      println("aaa")
      "a"
    } else "b"
    println(x)
    if(a){
      println("a")
      println("a")}
    else
    println("b")

  }
}

object l0202 {
  def main(args: Array[String]): Unit = {
    var a = 1
    a=2
    var b = if(a==1)
      "abc"
    else
      123
    b="bbb"
    b=222
    println(b)
    var c = if(a==1)
      123
    else
      456
//    c="ccc"
    println(c)
  }
}

object l0203 {
  def main(args: Array[String]): Unit = {
    for(i<-1 to 10){
      print(i+",")
    }
    for(i<-1 until 10){
      print(i+",")
    }
    var a = List(1,2,3,4,5)
    println("")
    for(i<-a){
      print(i+",")
    }
    println("")
    for(i<- Range(1,10)){
      print(i+",")
    }
    println(Range(1,10).toList)
    println((1 until 10).toList)
    println(Range(2,12,2).toList)
    println(List.fill(5)(0))
    println(List.fill(5)(math.random()))
    List.fill(5)(1).foreach(println)
    (1 to 10).foreach(println)
  }
}

object l0204 {
  def main(args: Array[String]): Unit = {
    for(i <- Range(1,18,2)){
      println(" "*((18-i)/2)+"*"*i)
    }
    println("@"*5)
    for(i<- 1 to 10 if(i%3==0) ){
      print(i+",")
    }
    (1 to 10).toList.filter(x=>x%3==0).foreach(println)

    var a = for(i<-1 to 10) i*2
    println(a)

    var b = for(i<-1 to 10) yield i*2
    println(b)

    var c = Array.fill(5)(0)
    println(c.toList)
  }
}
object l0205 {
  def main(args: Array[String]): Unit = {
    Breaks.breakable {
      for (i <- 1 to 10) {
        if (i % 5 == 0) {
          println("a")
          Breaks.break()
        }
      }
    }
  }
}

object l0206 {
  def main(args: Array[String]): Unit = {
    def f1()={
      println("f1")
    }
    f1()
    f1
    def f2(){
      println("f2")
    }
    f2()
    f2
    def f3 {
      println("f3")
    }
    f3
    f3
    println("a")
    var f4 = {
      println("f4")
    }
    println("b")
    println(f4)
    println(f4)
    println(f4)
    println("c")

    println("a")
    var f5 = ()=>{
      println("f5")
    }
    println("b")
    println(f5())
    println(f5)
    println(f5)
    println("c")

    def f6={
      println("f6")
    }
    f6
    f6

  }
}

object l0207 {
  def main(args: Array[String]): Unit = {
    def f1(x:Int)={
      x+1
    }
    println(f1(2))
    def f2(x:Int=2)={
      x+2
    }
    println(f2())
    println(f2(1))
    def f3(x:Int=1, y:Int=2)={
      (x,y)
    }
    println(f3())
    println(f3(3,4))
    println(f3(5))
    def f4(x:Int=2,y:Int)={
      (x,y)
    }
    println(f4(y=1))
    println(f4(2,3))
    println(f4(y=3,x=4))

    def f5(x:Int*)={
      x
    }
    println(f5(1,2,3,4,5))
    def f6(x:Int,y:Int*) ={
      (x,y)
    }
    println(f6(1,2,3,4,5,6))
  }
}

object l0208 {
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

object l0209 {
  def main(args: Array[String]): Unit = {
    def f1(x:Int) = {
      x+1
    }
    def f2(x:Int) = {
      x+2
    }
    def f3(fx:Int=>Int, y:Int) = {
      fx(y)
    }
    println(f3(f1,10))
    println(f3(f2,10))
    def f4(xstr:String):Int=>Int = {
      if(xstr=="+1"){
        f1 _
      } else {
        f2 _
      }
    }
    println(f4("+2")(2))
    def f5():Int=>Int = {
      f1 _
    }
    println(f5()(3))
  }
}

object l0210 {
  def main(args: Array[String]): Unit = {
    def mul(x:Int)(y:Int) = x*y
    val mul2 = mul(2) _
    val mul3 = mul(3) _
    println(mul(2)(3))
    println(mul2(4))
    println(mul3(5))
  }
}

object l0211 {
  var a = 10
  def main(args: Array[String]): Unit = {
    var b = 20
    def f1(x:Int)={
      def f2(y:Int) = {
        def f3(z:Int) ={
          a+=1
          (x,y,z, a, b)
        }
        f3 _
      }
      f2 _
    }
    println(f1(1)(2)(3))
    println(a)
    (()=>{
      a+=1
    })()
    println(a)
  }
}