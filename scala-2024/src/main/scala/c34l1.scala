import scala.io.Source

object c34l1 {
  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("input/books/the_old_man_and_the_sea.txt", "UTF-8").getLines().toArray
    source
      .flatMap(x => x.trim().split(" "))
      .map(x => (x, 1))
      .groupBy(x => x._1)
      .map(x => (x._1, x._2.length))
      .toList.sortBy(x => x._2)
      .foreach(x => print(x))

  }

}

object c34l12 {
  def main(args: Array[String]): Unit = {
    var a:String =  null
    a = 123

    println(a)
  }
}