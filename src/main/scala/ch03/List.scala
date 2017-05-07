package ch03

import scala.annotation.tailrec

/**
  * Created by pi on 2017. 5. 7..
  */
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ints: List[Int]): Int = ints match {
    case Nil => 1
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  def foldRight[A](as: List[A], z:A)(f: (A, A) => A): A = as match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f))
  }

  def foldLeft[A](z: A, as: List[A])(f: (A, A) => A): A = as match {
    case Nil => z
    case Cons(x, xs) => foldLeft(f(z, x), xs)(f)
  }

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = {
    @tailrec
    def has(sp: List[A], sb: List[A]): Boolean = (sup, sub) match {
        case (_, Nil) => true
        case (Cons(x, xs), Cons(y, ys)) if x == y => has(xs, ys)
        case _ => false
      }
    sup match {
      case Nil => false
      case xss@Cons(x, xs) => if (has(sup, sub)) true else hasSubsequence(xs, sub)
    }

  }

  def map[A,B](xs: List[A])(f: A => B): List[B] = xs match {
    case Nil => Nil
    case Cons(a, as) => Cons(f(a), map(as)(f))
  }

  def append[A](as: List[A], bs: List[A]): List[A] = as match {
    case Nil => bs
    case Cons(x, xs) => Cons(x, append(xs, bs))
  }

  def concat[A](ass: List[List[A]]): List[A] = foldRight(ass, Nil)(append)

  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = {
    concat(map(as)(f))
  }

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

