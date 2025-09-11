import scala.io.Source

object C56WC {
  def main(args: Array[String]) {
    val source = Source.fromFile("input/books/the_old_man_and_the_sea.txt", "UTF-8").getLines().toArray
//    source.foreach(println(_))
    source
      .flatMap(x => x.trim().split(" "))
      .map(x => (x, 1))
      .groupBy(x => x._1)
      .map(x => (x._1, x._2.length))
      .toList.sortBy(x => x._2)
      .foreach(x => println(x))
//      .foreach(println(_))
  }
}

