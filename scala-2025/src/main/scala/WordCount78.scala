import scala.io.Source

object WordCount78 {
  def main(args: Array[String]) {
    val source = Source
      .fromFile("input/books/the_old_man_and_the_sea.txt", "UTF-8")
      .getLines()
      .toArray
    source
      .flatMap(x => x.trim().split(" ").toList)
      .map(x => (x, 1))
      .groupBy(x => x._1)
      .map(x=>(x._1,x._2.toList))
//      .map(x => (x._1, x._2.length))
      .map(x=>(x._1, x._2.map(_._2).sum))
      .toList
      .sortBy(x => x._2)
      .map(println(_))
  }
}
