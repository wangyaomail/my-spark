package a.b

class x {

}

package c {

  class y {}

  package d {
    class z {

    }

  }
  object x {
    def main(args: Array[String]): Unit = {
    }
  }
}

object e {
  def main(args: Array[String]): Unit = {
    var x ="123"
    var y ="123"
    print(x==y)
    x = new String("abc")
    y = new String ("abc")
    println(x==y)
    println(x>y)
    y = "abb"
    println(x>y)
  }
}