object varValTest {
  def main(args: Array[String]): Unit = {
    var a = 1
    val b = 2
    var c = new vvv()
    val d = new vvv()
    c = new vvv()

    c.x=100
    d.x=200

    println(c.x, d.x)

    var a1 = 1
    var a2:Long = 1
    var a3:Short = 1
    var b1 = 1.0
    var b2:Float = 1

    var c1=  true
    var c2 = false

    var d1 = 'd'
    var d2 = '\u0061'
    var d3 = '\\'

    var d4 = "abcd"

    var d5 =
      """
         hello
         world
      """

  }
}

class vvv {
  var x=1;
  val y=2;
}