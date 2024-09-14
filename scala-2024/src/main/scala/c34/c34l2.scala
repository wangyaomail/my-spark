package c34

object c34l21{
  def main(args: Array[String]): Unit = {
    var a = 1
    var b = 2
    println(a+b)
    println(a-b)
    println(a*b)
    println(a/b)
    println(a%b)

    var c = "123"
    var d = "123"
    var e = "12"+"3"
    println(c==d)
    println(c==e)
    var f = new String("123")
    var g = new String("123")
    println(f==g)
    println("abc">"abd")
    println("abc"<"abd")

    if (f == g ) {
      println(f == g)
      println(f == g)
    }
    else {
      println(f==g)
    }
  }
}

object c34l22{
  def main(args: Array[String]): Unit = {
    //    def f1:Any = if(true) 1
    //    println(f1)
    //
    //    var a = 100
    //    def f2:Any = if(true) {a+=10;println(a);a}
    //    a+=1
    //    println(f2)

    var b = 100
    var c:Int = {
      b+=1
      println(b)
      b
    }
    b+=1
    println(c)
    println(b)

    println( 1 to 10)
    println( 1 until 10)

    for(i<- 1 to 10){
      print(i+",")
    }

  }
}