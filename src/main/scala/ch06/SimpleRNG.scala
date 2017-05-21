package ch06

/**
  * Created by pi on 2017. 5. 21..
  */
case class SimpleRNG(seed: Long) extends RNG {
  type Rand[+A] = RNG => (A, RNG)

  override def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0XFFFFFFFFFFFFL
    val nextRNG = SimpleRNG(newSeed)
    val n = (newSeed >>>16).toInt
    (n, nextRNG)
  }
  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] = rng => (a, rng)

  def map[A,B](rand: Rand[A])(f: A => B): Rand[B] = rng => {
    val (a, rng2) = rand(rng)
    (f(a), rng2)
  }

  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
    val (a, rng2) = ra(rng)
    val (b, rng3) = rb(rng2)
    (f(a, b), rng3)
  }

  def both[A,B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] = map2(ra, rb)((_, _))

  def flatMap[A,B](rand: Rand[A])(f: A => Rand[B]): Rand[B] = rng => {
    val (a, rng2) = rand(rng)
    f(a)(rng2)
  }

  def double: Rand[Double] = map(int)(_.toDouble / Double.MaxValue)

  def intDouble: Rand[Double] = flatMap(int)(i => rng => (i.toDouble, rng))
}


object SimpleRNG {

  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (n, rng2) = rng.nextInt
    (math.abs(n), rng2)
  }

  def main(args: Array[String]): Unit = {
    val rng = SimpleRNG(1)
    val (n, rng2) = rng.nextInt
    println(n)
    val (n2, rng3) = nonNegativeInt(rng2)
    println(n2)
    val (d, rng4) = rng.intDouble(rng3)
    println(d)
//    val r = for {
//      x <- rng.int
//      y <- rng.double
//    } yield (x, y)
//    println(r)
  }
}