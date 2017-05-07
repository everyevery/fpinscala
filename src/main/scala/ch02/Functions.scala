package ch02

import scala.annotation.tailrec

/**
  * Created by pi on 2017. 5. 6..
  */
object Functions {
  def formatAbs(i: Int): Unit = {
    val v = math.abs(i)
    println(f"$v%d")
  }

  def factorial(i: Int): Int = {
    @tailrec
    def fact(a: Int, i: Int): Int =
      if (i<1) a
      else fact(a * i, i-1)
    fact(1, i)
  }
  def formatFactorial(i: Int): Unit = {
    val v = factorial(i)
    println(f"$v%d")
  }
  def format(i: Int, f: (Int => Int)): Unit = {
    val v = f(i)
    println(f"$v%d")
  }

  def curry[A,B,C](f: (A, B) => C): A => (B => C) = {
    a => (b => f(a, b))
  }

  def uncurry[A,B,C](f: A => B => C): (A, B) => C = {
    (a, b) => f(a)(b)
  }

  def compose[A,B,C](f: A => B)(g: B => C): A => C = (x => g(f(x)))

  def add2(a: Int, b: Int): Int = a + b

  val add: (Int, Int)=> Int = _ + _

  def addOne: Int => Int = add.curried(1)
  def addTwo: Int => Int = (add2 _).curried(2)

  def main(args: Array[String]): Unit = {
    formatAbs(-3)
    formatFactorial(3)
    format(3, factorial)
    assert(add(1, 2) == uncurry(curry(add))(1, 2))
    val three: Int = add.curried(1)(2)
    println(three)
  }
}
trait PlaceholderExample {
  def process[A](f: A => Unit)

  val set: Set[_ => Unit]

  set.foreach(process(_)) // No Error
}
