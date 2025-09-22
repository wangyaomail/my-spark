object while循环56 {
  def main(args: Array[String]): Unit = {
    var x =10
    while(x>0){
      x-=1
      println(x)
    }

    do{
      x+=1
    } while(x<10)
    x =10
    var a = while(x>0){
      x -= 1
    }
    println("------")
    println(a)
  }
}

object 函数561 {
  def main(args: Array[String]): Unit = {
    def f11() = {
      println("f11")
    }
    f11
    f11()

    def f12 = {
      println("f12")
    }
    f12

    println(f11,f12)

    def f3() {
      println("f3")
    }

    f3
    f3()

    def f32 {
      println("f3")
    }

    f32
//    f32()

//    def f5(a:Int)={
//      println(a)
//    }
    def f5(a:Int, b:Int)={
      println(a+b)
    }
    f5(1,2)

    def f6(a:Int, b:Int) {
      println(a+b)
    }
    f6(1,2)

    def f7 ()={
      "f7"
    }
    def f8() {
      "f8"
    }
    def f82():Unit= {
      "f8"
    }
    println(f7)
    println(f8, f82)

    def f9{
      "f9"
    }
    println(f9)

    def f10 = {
      "f10"
    }
    println("f10")


  }
}

object 函数562 {
  def main(args: Array[String]): Unit = {
    def f11(a:Int, b:Int)={
      a+b
    }
    println(f11(1,2))

    def f12(a:Int, b:Int=10)={
      a+b
    }
    println(f12(1))
    println(f12(1,2))

    def f122(a:Int=10, b:Int)={
      a+b
    }
//    println(f122(1))
    println(f122(1,2))
    println(f122(b=2))

    def f123(a:Int=10, b:Int,c:Int=10,d:Int)={
      a+b+c+d
    }
    println(f123(1,2,3,4))
    println(f123(b=1,d=2,c=5))

    def f14(a:Int*):Double={
//      a.sum
//      a.max
      a.sum/a.length
    }
    println(f14(1,2,3,4,5))


  }
}


object 递归56 {
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

object 高阶函数56 {
  def main(args: Array[String]): Unit = {
    def f1 = {
      "f1s"
    }
    var f2 = {
      "f2s"
    }
    println(f1, f2)

    var f3 = ()=>"f3s"
    println(f3)
    println(f3())

    def f4(f:(Int,Int)=>Int)={
      f
    }
    println(f4((a,b)=>a+b))
    println(f4((a,b)=>a+b)(1,2))

    def f5(a:Int, b:Int) = {
      ()=>a+b
    }
    println(f5(2,3)())

    def f6(a:Int) = {
        (b:Int)=>{
          (c:Int) => {
            a+b+c
          }
        }
    }
    // 1+2+3
    println(f6(1)(2)(3))

    def f7(a:Int, b:Int)={
      def f8={
        a+b
      }
      f8 _
    }

    println(f7(2,3))
    println(f7(2,3)())

  }
}

object 柯力化56 {
  def main(args: Array[String]): Unit = {
    def f1(a:Int) ={
      def f2(b:Int)={
        a+b
      }
      f2 _
    }
    println(f1(1)(2))

    def f3(a:Int)(b:Int) = {
      a+b
    }
//    println(f3(1))
    println(f3(1)(2))

    def mul(a:Int)(b:Int) = {
      a*b
    }
    def mul2 = mul(2) _
    println(mul(2)(3))
    println(mul2(3))
  }
}

object 闭包56 {
  var y = 1
  var x = 3
  def main(args: Array[String]): Unit = {
    def f1(a:Int) = {
      x*a*y*z
    }
//    var x = 2
    println(f1(1))
  }
  var z = 1
}

class 面向对象56 {
  var x = 1;
  val y =2;
//  var z:Int=null;
  var m:Int=_;
  def f1  = {
    println("hello")
  }

}

object 面向对象56 {
  var aaa = 100
  var bbb = 200

  def main(args: Array[String]): Unit = {
    println(aaa,bbb)
    println(new 面向对象56().x)
    var obj = new 面向对象56()
//    obj.z = 1
    // POJO
  }
}






