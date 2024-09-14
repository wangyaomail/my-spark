package c34

import java.util


object c34l3 {
  def main(args: Array[String]): Unit = {
    for( i<- Range(1,10)){
      println(i)
    }
    for( i<- Range(1, 10, 2)){
      println(i)
    }
    for(i <- Range(1,18,2)){
      println(" "*((18-i)/2)+"*"*i)
    }
    print("#"*10)
    println("")
    for(i<-1 to 10 if i % 2 ==0){
      println(s"$i")
    }
    for(i <- Range(1, 18, 2) ; j=(18-i)/2 ; y=i*2 ; z=i*3){
      println(" "*j+"*"*i + "\t"+y + "\t"+z)
    }
    for(i <- Range(1, 18, 2)){
      var j=(18-i)/2 ; var y=i*2 ; var z=i*3
      println(" "*j+"*"*i + "\t"+y + "\t"+z)
    }
  }
}

object c34l31 {
  def main(args: Array[String]): Unit = {
    var xarr = for( i<- 1 to 10)  yield i+3
    println(xarr)
    import scala.util.control.Breaks
    var j=10
    Breaks.breakable(
      for(i <- 1 to 10){
        j += 1
        if ( i ==5){
          Breaks.break()
        }
        println(i)
      }
    )
    println(j)

    while(j <20){
      println(j)
      j+=1
    }
    println("")
    do{
      println(j)
      j+=1
    }while(j<30)
  }
}

object c34l33{
  def main(args: Array[String]): Unit = {
    def f1()={
      println("hello")
      123
    }
    var x = f1
    println(x)
    def f2  {
      println("hello")
    }
    f2
    def f3 = "hello"
    println(f3)
    var f4 = "hello"
    println(f4)

    def f5(x:String, y:Int=1) = {
      println(x, y)
    }
    f5("f5", 5)
    f5("f5")
    def f6(x:String="x", y:Int) = {
      println(x, y)
    }
    f6("f6", 1)
    f6(y=2)

    def f7(b:String,a:Int*): Unit = {
      print(b+":")
      for(i<-a){print(i+",")}
      println("")
    }
    f7("x",1)
    f7("y",1,2,3)
    f7("z",1,2,3,4,5,6,7,8)
  }
}

object c34l34{
  def main(args: Array[String]): Unit = {
    def f1(f2:(Int)=>Int): Int = {
      f2(3)+10
    }
    def f3(x:Int=2):Int={
      1+x
    }
    println(f1(f3))

    var x=0
    def f4()={
      x += 1
      println("f4", x)
      "f4"
    }
    def f5()={
      x+=1
      println("f5", x)
      f4 _
    }
    x+=1
    println("main", x)
    println(f5)
    def f6():()=>String={
      x+=1
      println("f5", x)
      f4
    }
    println(f6)
  }
}

object c34l35{
  def main(args: Array[String]): Unit = {
    def f1(a:Int)={
      def f2(b:Int)={
        a+b
      }
      f2 _
    }
    println(f1(1)(2))
    def f3(a:Int)(b:Int) ={
      a+b
    }
    println(f3(1)(2))
    def f4 = f3(3) _
    var f5 = f3(2) _
    println(f4(5))
    println(f5(4))
  }
}

object c34l36{
  var x =0
  def main(args: Array[String]): Unit = {
    println(x)
  }
}
class c34l36{
  var y = 2

}

class outa{
  var name="outa"
}
package p1{

  class p1a {
    var name = "p1a"
  }
  package p2 {
    class p2a{
      var name="p2a"
    }
    object p2main {
      def main(args: Array[String]): Unit = {
        println(new outa().name)
        println(new p1a().name)
        println(new p2a().name)
      }
    }
  }
  object p1main {
    def main(args: Array[String]): Unit = {
      import c34.p1.p2.p2a
      println(new outa().name)
      println(new p1a().name)
      println(new p2a().name)
    }
  }
}
object p2main {
  def main(args: Array[String]): Unit = {
    import c34.p1.p1a
    import c34.p1.p2.p2a
    println(new outa().name)
    println(new p1a().name)
    println(new p2a().name)
  }
}

object c34l37{
  def main(args: Array[String]): Unit = {
    import java.util.{ArrayList, HashMap}
    var arr = new ArrayList()
    var map = new HashMap()

    import java.util.{Date=>utilDate}
    import java.sql.{Date=>sqlDate}
    println(new utilDate())
    println(new sqlDate(System.currentTimeMillis()))



  }
}
