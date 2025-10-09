package c78
package t6

object map算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4)
    l1.map(_+1).foreach(println)
  }
}

object reduce算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7)
//    println(l1.reduce(_+_))
//    println(l1.reduce((x,y)=>{
//      println(x,y)
//      x+y
//    }))
//    println(l1.reduceLeft((x,y)=>{
//      println(x,y)
//      x+y
//    }))
//    println(l1.reduceRight((x,y)=>{
//      println(x,y)
//      x+y
//    }))
    var l2 = List("a","b","c","d")
    println(l2.reduce(_+_))
    println(l2.reduceLeft(_+_))
    println(l2.reduceRight(_+_))
    println(l2.reduce((x,y)=>(y+x)))

  }
}

object fold算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7)
        println(l1.fold(1)(_+_))
        println(l1.fold(1)((x,y)=>{
          println(x,y)
          x+y
        }))
    var l2 = List("a","b","c","d")
    println(l2.fold("e")(_+_))
    println(l2.foldLeft("e")(_+_))
    println(l2.foldRight("e")(_+_))

  }
}

object sort算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,4,3,2)
    println(l1.sorted)
    var l2 = List("a","c","d","b")
    println(l2.sorted)
    var l3 = List((3,"b"),(1,"d"),(2,"c"),(4,"a"))
    println(l3.sortBy(_._1).reverse)
    println(l3.sortBy(_._2).reverse)

    println(l3.sortWith{
      case (x,y) =>x._1>y._1
    })
  }
}

object filter算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7)
    println(l1.filter(_%2!=0))
    println(l1.filterNot(_%2==0))
  }
}

object count算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1.size)
    println(l1.length)
    println(l1.count(_%2==0))
    println(l1.count(x=>true))
  }
}

object diff算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6,7)
    var l2 = List(5,6,7,8,9,10)
    println(l1.diff(l2))
    println(l1.union(l2).distinct)
    println(l1.intersect(l2))

  }
}

object groupby算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List((3,"b"),(1,"d"),(2,"c"),(4,"a"),
      (3,"c"),(1,"a"),(2,"b"),(4,"d"))
    println(l1.groupBy(_._1))
    println(l1.groupBy(_._2))
    println(l1.grouped(3).toList)
    println(l1.sliding(3).toList)
  }
}

object scan算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1.fold(1)(_+_))
    println(l1.scan(1)(_+_))
    println(l1.scanLeft(1)(_+_))
    println(l1.scanRight(1)(_+_))
  }
}

object take算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1.take(30))
//    println(l1.takeLeft(3))
    println(l1.takeRight(3))
    println(l1.takeWhile(_<4))
    println(l1.takeWhile(_%2==0))
    println(l1.takeWhile(_%2!=0))

    println(l1.slice(2,4))
  }
}

object drop算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(0,1,2,3,4,5)
    println(l1.drop(3))
//        println(l1.dropLeft(3))
    println(l1.dropRight(3))
    println(l1.dropWhile(_<4))
    println(l1.dropWhile(_%2==0))
    println(l1.dropWhile(_%2!=0))
  }
}

object span算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5,6)
    println(l1.span(_<3))
    var x = l1.span(_<3)._1
    println(x)
    var (a,b) = l1.span(_<3)
    println(a)
    println(b)

    println(l1.splitAt(3))
    println(l1.partition(_%2==0))
  }
}

object padTo算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4)
    println(l1.padTo(10,1))
    println(l1.padTo(10,"abc"))
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
    var l1 = List(1,2,3,4)
    var l2 = List("a","b","c","d")
    println(l1.zip(l2))
    println(l2.zipWithIndex)
    println(l2.zip(Range(1,1000)))
    println(l2.zip(Range(1,3)))
    println(l1.zip(l2).unzip)

  }
}

object update算子 {
  def main(args: Array[String]): Unit = {
    var l1 = List(1,2,3,4,5)
    println(l1.updated(3,10))
    println(l1)
  }
}