package c56

import scala.util.control.Breaks

object c56l3 {
  def main(args: Array[String]): Unit = {
    for(i<- Range(1,10)){
      println(i)
    }

    for(i<- Range(1,10,2)){
      println(i)
    }

    for(i <- Range(1,18,2)){
      println(" "*((18-i)/2)+"*"*i)
    }

    Breaks.breakable{
      for(i<- Range(7,100,7)){
        if(i%3==0){
          println(i)
          Breaks.break()
        }
      }
    }

    for(i<-Range(7,100,7);a=i*2;if(i%3==0);b=i*3;c=i*3;d=i*3;e=i*3){
      println(i,a,b,c,d,e)
    }

    var x = for(i<- Range(1,10)) yield i
    println(x)

    for(j<- for(i<- Range(1,10)) yield i*2)      {
      println(j)
    }

    for (k<- for(j<- for(i<- Range(1,10)) yield i*2) yield j*2){
      println(k)
    }

    var i=0
    while(i<10){
      i+=1
      println(i)
    }

    do{
      i+=1
      println(i)
    }while(i<20)
  }
}

object c56l31{
  def main(args: Array[String]): Unit = {
    def f1 (x:Int) = {
      println(x)
    }
    f1(1)
    def f2 (x:Int=1, y:Int) = {
      println(x+y)
    }
    f2(1,2)
    f2(y=1)
    def f3()={
    }
    println(f3)
    def f4(a:Int*): Unit = {
      println(a)
    }
    f4(1)
    f4(1,2)
    f4(1,2,3)

    var farr = new Array[Int](100)
    def f5(a:Int):Int = {
      if(a<=0){
        farr(0) = 1
        farr(0)
      } else {
        farr(a) = f5(a - 1) + f5(a - 2)
        farr(a)
      }
    }
    f5(30)
//    for(i<- farr;if(i<10)) println(i)
    farr.foreach(println)
  }
}

object c56l32{
  def main(args: Array[String]): Unit = {
    def f1(x:Int=>Int) = {
      x
    }
    def f2(y:Int)={
      y+1
    }
    println(f1(f2))

    def f3() = {
      println("hello f3")
    }
    var f5 = {
      println("hello f5")
    }
    def f4():()=>Unit={
      f3
    }
    println(f4)
    f5

    def mul(a:Int,b:Int)={
      a*b
    }
    println(mul(2,3))
    def mul2(a:Int) = {
      mul(a,2)
    }
    def mulklh(a:Int)(b:Int) = {
      a*b
    }
    println(mulklh(4)(5))
    def mul2klh = {mulklh(2) _}

    println(mul2klh(5))

  }
}

object c56l33{
  def main(args: Array[String]): Unit = {
    var l33 = new c56l33()
    println(l33.name)
    l33.sayhi()
  }
}

class c56l33{
  var name = "33"
  println("hello")
  def sayhi()={
    println("hi")
  }
}

class a_out {
  def sayhi = ()=> println("a_out")
}
object x{
  def main(args: Array[String]): Unit = {
    new a_out().sayhi()
    import c56.a.a_in
    new a_in().sayhi()
    import c56.a.b.b_in
    new b_in().sayhi()
  }
}
package a{
  class a_in {
    def sayhi = ()=> println("a_in")
  }
  object x{
    def main(args: Array[String]): Unit = {
      new a_out().sayhi()
      new a_in().sayhi()
      import c56.a.b.b_in
      new b_in().sayhi()
    }
  }
  package b{
    class b_in {
      def sayhi = ()=> println("b_in")
    }
    object x{
      def main(args: Array[String]): Unit = {
        new a_out().sayhi()
        new a_in().sayhi()
        new b_in().sayhi()
      }
    }
  }
  package c{
    class c_in {
      def sayhi = ()=> println("c_in")
    }
    object x{
      def main(args: Array[String]): Unit = {
        new a_out().sayhi()
        new a_in().sayhi()
        import c56.a.b.b_in
        new b_in().sayhi()
      }
    }
  }
}



