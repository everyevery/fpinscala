package ch01

/**
  * Created by pi on 2017. 5. 6..
  */
class Cafe {
  def buyCoffee(cc: CreditCard): Coffee = {
    val cup = Coffee()
    cc.charge(cup.price)
    cup
  }
}

class Cafe2 {
  def buyCoffee(cc: CreditCard): (Coffee, Charge) = ???

  def buyCoffee(cc: CreditCard, n: Int): (List[Coffee], Charge) = ???
}

object Cafe {
  def main(args: Array[String]): Unit = {
    val cafe2 = new Cafe2()
    val cc = new CreditCard("jongsoo.lee")
    val (coffees, charges) = cafe2.buyCoffee(cc, 10)
    println(s"${coffees.size}, ${charges.amount}")
  }
}
