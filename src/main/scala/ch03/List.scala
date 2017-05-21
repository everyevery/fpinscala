package ch03

import scala.annotation.tailrec

/**
  * Created by pi on 2017. 5. 7..
  */
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ???

  def product(ints: List[Int]): Int = ???

  def apply[A](as: A*): List[A] = ???

  def foldRight[A](as: List[A], z:A)(f: (A, A) => A): A = ???

  def foldLeft[A](z: A, as: List[A])(f: (A, A) => A): A = ???

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = ???

  def map[A,B](xs: List[A])(f: A => B): List[B] = ???

  def append[A](as: List[A], bs: List[A]): List[A] = ???

  def concat[A](ass: List[List[A]]): List[A] = ???

  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = ???

  def main(args: Array[String]): Unit = {
    val as: List[Int] = List(1, 2, 3, 4, 5)
    val bs: List[Int] = List(0, 0, 3, 4, 5)
    assert(sum(as) == 15)
    assert(product(as)==120)
    assert(foldRight(as, 0)(_ + _) == 15)
    assert(foldRight(as, 1)(_ * _) == 120)
    assert(foldRight(bs, 1)(_ * _) == 0)
    assert(hasSubsequence(List(1, 2, 3, 4, 5), List(2, 3, 4)) == true)
    assert(hasSubsequence(List(1, 2, 3, 4, 5), List(2, 3, 3)) == false)
    assert(flatMap(List(1, 2, 3))(i => List(i, i)) == List(1, 1, 2, 2, 3, 3))
  }
}

