package ch03

import scala.beans.BeanProperty

/**
  * Created by pi on 2017. 5. 7..
  */
sealed trait Tree[+A]
case class Leaf[A](@BeanProperty value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {

  def map[A,B](ts: Tree[A])(f: A => B): Tree[B] = ???

  def main(args: Array[String]): Unit = {
    val a = Leaf(10)
    println(a.getValue)
  }
}
