package ch05

import scala.annotation.tailrec

/**
  * Created by pi on 2017. 5. 11..
  */
sealed trait Stream[+A] {
  def headOption: Option[A] = this match {
    case Cons(h, _) => Some(h())
    case _ => None
  }

  def toList: List[A] = this match {
    case Cons(h, t) => h() :: t().toList
    case _ => List()
  }

  def toList2: List[A] = {
    @tailrec
    def go(s: Stream[A], acc: List[A]): List[A] = s match {
      case Cons(h, t) => go(t(), h() :: acc)
      case _ => acc
    }

    go(this, List()).reverse
  }

  def take(n: Int): Stream[A] = this match {
    case Cons(h, t) if n != 0 => Cons(h, () => t().take(n - 1))
    case _ => Empty
  }

  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Cons(h, t) if p(h()) => Cons(h, () => t().takeWhile(p))
    case _ => Empty
  }

  def drop(n: Int): Stream[A] = this match {
    case Cons(_, t) if (n > 0) => t().drop(n - 1)
    case _ => this
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h, t) => f(h(), t().foldRight(z)(f))
    case _ => z
  }
}

case object Empty extends Stream[Nothing]

case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

  def constant[A](a: A): Stream[A] = cons(a, constant(a))

  def fibs: Stream[Int] = {
    def go(f0: Int, f1: Int): Stream[Int] = cons(f0, go(f1, f0 + f1))

    go(0, 1)
  }

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] =
    f(z) match {
      case Some((a, s)) => cons(a, unfold(s)(f))
      case None => Empty
    }

  def main(args: Array[String]): Unit = {
    val s = cons(1, cons(2, Empty))
    println(constant(10).take(10))
    println(cons(1, cons(2, cons(3, cons(4, ???)))).take(2))
    println(fibs.take(10))
  }
}
