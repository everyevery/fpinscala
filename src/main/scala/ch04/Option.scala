package ch04

/**
  * Created by pi on 2017. 5. 9..
  */
sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case Some(x) => Some(f(x))
    case _ => None
  }
  def flatMap[B](f: A => Option[B]): Option[B] = this match {
    case Some(x) => f(x)
    case _ => None
  }
  def getOrElse[B >: A](default: => B): B = this match {
    case Some(x) => x
    case _ => default
  }
  def orElse[B >: A](ob: => Option[B]): Option[B] = this match {
    case None => ob
    case _ => this
  }
  def filter(f: A => Boolean): Option[A] = this match {
    case Some(x) if (f(x))  => this
    case _ => None
  }
}
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f

  def insuranceRateQuote(age: Int, numberOfSpeedingTickets: Int): Double = age * numberOfSpeedingTickets / 100.0

  def Try[A](a: => A): Option[A] = try Some(a) catch { case e: Exception => None }

  def map2[A,B,C](ao: Option[A], bo: Option[B])(f: (A, B) => C): Option[C] = (ao, bo) match {
    case (Some(a), Some(b)) => Some(f(a,  b))
    case _ => None
  }

  def map2_1[A,B,C](ao: Option[A], bo: Option[B])(f: (A, B) => C): Option[C] =
  ao flatMap (aa => bo map (bb => f(aa, bb)))

  def sequence[A](a: List[Option[A]]): Option[List[A]] = a match {
    case Nil => Some(Nil)
    case h :: t => h flatMap(ha => sequence(a) map ( ha :: _))
  }

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = a match {
    case Nil => Some(Nil)
    case (h :: t) => map2(f(h), traverse(t)(f))(_ :: _)

  }

  def parseInsuranceRateQuote(age: String, numberOfSpeedingTickets: String): Option[Double] = {
    val ageOpt: Option[Int] = Try(age.toInt)
    val ticketsOpt: Option[Int] = Try(numberOfSpeedingTickets.toInt)
    map2(ageOpt, ticketsOpt)(insuranceRateQuote)
  }

  def main(args: Array[String]): Unit = {
    println(parseInsuranceRateQuote("100", "200"))
    println(parseInsuranceRateQuote("a", "200"))
  }
}

