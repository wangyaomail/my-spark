package c56
package t6

object reduce算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
//    l1.map(_+1).foreach(println)
//    println(l1.reduce((x,y)=>{
//      println(x,y)
//      x+y
//    }))
    println("reduceLeft",
      l1.reduceLeft((x,y)=>{
            println(x,y)
            x+y
          }))
    println("reduceRight",
      l1.reduceRight((x,y)=>{
      println(x,y)
      x+y
    }))
  }
}

object fold算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1.fold(1)((x,y)=>{
      println(x,y)
      x+y
    }))
    println(l1.foldLeft(1)((x,y)=>{
      println(x,y)
      x+y
    }))
    println(l1.foldRight(1)((x,y)=>{
      println(x,y)
      x+y
    }))
  }
}

object sort算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(4,2,5,1,3)
    l1.sorted.foreach(println)
    var l2 = List("a","c","d","b")
    l2.sorted.foreach(println)
    var l3 = List((4,"a"),(2,"c"),(3,"d"),(1,"b"))
//    l3.sorted.foreach(println)
    l3.sortBy(_._2).foreach(println)
    l3.sortWith{case (x,y)=>x._1<y._1}.foreach(println)
  }
}

object filter算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7,8)
    l1.filter(_%2==1).foreach(println)
//    l1.filterNot(_%2==1).foreach(println)
    l1.filter(_%2!=1).foreach(println)
  }
}

object count算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4)
    println(l1.size)
    println(l1.length)
    println(l1.count(x=>true))
    println(l1.count(_%2==0))
    println(l1.filter(_%2==0).size)
  }
}

object diff算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4)
    var l2 = List(3,4,5,6)
    println("diff",l1.diff(l2))
    println("union",l1.union(l2))
    println("union-dis",l1.union(l2).distinct)
    println("intersect",l1.intersect(l2))
  }
}


object groupBy算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List((3,"a"),(1,"a"),(3,"b"),(1,"b"))
    println(l1.groupBy(_._1))
    println(l1.groupBy(_._2))
    println(l1.grouped(2).toList)
    println(l1.grouped(3).toList)
  }
}

object scan算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4)
    println(l1.scan(0)(_+_))
    println(l1.scan(1)(_+_))
    println(l1.scanLeft(1)(_+_))
    println(l1.scanRight(1)(_+_))
  }
}

object take算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7)
    println(l1.take(3))  // [:3,3:]
    println(l1.takeRight(3))  // [3:]
    println(l1.takeWhile(_<5))
  }
}

object drop算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7)
    println(l1.drop(3))
    println(l1.dropRight(3))
    println(l1.dropWhile(_<5))
  }
}

object span算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7)
    println(l1.span(_<5))
    println(l1.span(_%2!=0))
    println(l1.splitAt(3))
    println(l1.partition(_%2==0))
  }
}

object padTo算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3)
    println(l1.padTo(10,9))
    println(l1.padTo(10,"a"))
  }
}

object comb算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3)
    println(l1.combinations(2).toList)
    println(l1.permutations.toList)
  }
}

object zip算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    var l2 = List("a","b","c","d","e")
    println(l1.zip(l2))
    var l3 = List(1,2,3)
    println(l1.zip(l3))
    println(l1.zip(l2).unzip)
    var (x,y) = l1.zip(l2).unzip
    println(x)
    println(y)
    println(l1.zipWithIndex)
    println(l1.zip(Range(0,100).toList))
  }
}

object slice算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1.slice(2,4))
    println(l1.sliding(2).toList)
  }
}

object update算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1.updated(2,10))
  }
}