
object while测试 {
  def main(args: Array[String]): Unit = {
    var a = 10
    while(a>0){
      println(a)
      a-=1
    }

    do{
      a+=1
      println("dowhile",a)
    }while(a<10)


  }
}

object 函数78 {
  def main(args: Array[String]): Unit = {
    def f1()={
      println("f1")
    }
    f1()
    f1
    def f2 = {
      println("f2")
    }
//    f2()
    f2
    def f3 {
      println("f3")
    }
    f3

    var f4 = {
      println("f4")
    }
    f4

    var f41 = {
      println("f4")
      "f41返回值"
    }
    println(f41)
  }
}

object 函数782 {
  def f101 = {
    "f101"
  }
  def main(args: Array[String]): Unit = {
    println(f101)
    def f5(a:Int, b:Int) = {
      a+b
    }
    println(f5(1,2))

    def f6(a:Int, b:Int){
      println(a+b)
    }
    f6(1,2)

    def f7={
      "f7"
    }
    var f7s = f7
    println(f7s)


    def f8 {
      "f8"
    }
    var f8s = f8
    println(f8s)

    def f9(a:String) {
      "f9"+a
    }
    var f9s = f9("aaa")
    println(f9s)

    def f10 = {
      "f10"
    }
    var f10s = f10
    println(f10s)


  }
}

object 函数783 {
  def main(args: Array[String]): Unit = {
    def f11(a:Int, b:Int) = {
      a+b
    }
    println(f11(1,2))

    def f12(a:Int, b:Int=10) = {
      a+b
    }
    println(f12(1))
    println(f12(1,2))

    def f12a(a:Int, b:Int=10, c:Int=20, d:String="hi") = {
      println(d)
      a+b+c
    }
    println(f12a(1))
    println(f12a(1,2))
    println(f12a(1,2,3))
    println(f12a(1,2,3,"hello"))

    def f12b(a:Int=10, b:Int) = {
      a+b
    }
    println(f12b(b=10))
    println(f12b(1,2))

    def f13(a:Int*)={
      a.sum
    }
    println(f13(1,2,3))
    println(f13(1,2,3,4,5))
    println(f13(1,2,3,4,5,6,7))

  }
}

object 过程781 {
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

object 高阶函数78 {
  def main(args: Array[String]): Unit = {
    def f1() = {
      "f1"
    }
    def f2 = f1
    def f3 = {
      f1
    }
    f2
    f3

    def f4 = ()=>"1"
    println(f4)
    println(f4())

    def f5(f:(Int,Int)=>Int)={
      println(f(2,3))
    }

    f5((x:Int,y:Int)=>x+y)
    f5((x:Int,y:Int)=>x-y)
    f5((x:Int,y:Int)=>x*y)
    f5((x:Int,y:Int)=>x/y)

    def f6(a:Int)={
      ()=>a*3
    }

    println(f6(2))
    println(f6(2)())
    def f7(a:Int)={
      (x:Int) => {
        (y:Int) =>{
          a+x+y
        }
      }
    }
    println(f7(1)(2)(3))

    val a = {
      "123"
    }
    val b = ()=>{
      "123"
    }
    println(a,b)

    def f8 = {
      "f8"
    }

    def f9 = {
      f8 _
    }

    println(f9)




  }
}

object  柯力化78 {
  def main(args: Array[String]): Unit = {
    def f1(a:Int)={
      (b:Int) => a+b
    }
    println(f1(1))
    println(f1(1)(2))

    def f2 (a:Int)(b:Int) = {
      a + b
    }
//    println(f2(1))
    println(f2(1)(2))

    def mul(a:Int)(b:Int) = {
      a * b
    }
    def mul2 = {
      mul(2) _
    }

    println(mul(3)(4))
    println(mul2(3))




  }
}

object 闭包78 {
  def main(args: Array[String]): Unit = {
    var a = 10
    def f1 = {
      a+10
    }
    println(f1)
  }
}

class 面向对象78 {
  var a =10
  var b:String =_

}
object 面向对象78 {
  def main(args: Array[String]): Unit = {
    println(new 面向对象78().a)
  }
}

class 面向对象782 {
  var a =10

}

package object p10 {

}

package p1{
  class a {

  }
  package p2{
    class a {

    }

  }
  package p3{
    class a {

    }
  }
}