package ch04

/**
  * Created by pi on 2017. 5. 9..
  */
sealed trait Either[+L, +R] {
  def map[B](f: R => B): Either[L, B] = this match {
    case Left(a) => Left(a)
    case Right(r) => Right(f(r))
  }
  def flatMap[LL >: L, B](f: R => Either[LL, B]): Either[LL, B] = this match {
    case Left(a) => Left(a)
    case Right(r) => f(r)
  }
  def orElse[LL >: L, R](b: => Either[LL, R]): Either[LL, R] = this match {
    case Left(a) => Left(a)
    case Right(_) => b
  }
}

case class Left[+L](left: L) extends Either[L, Nothing]
case class Right[+R](right: R) extends Either[Nothing, R]

object Either {
  def main(args: Array[String]): Unit = {

  }
}