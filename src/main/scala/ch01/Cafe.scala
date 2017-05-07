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
  def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
    val cup = Coffee()
    (cup, Charge(cc, cup.price))
  }

  def buyCoffee(cc: CreditCard, n: Int): (List[Coffee], Charge) = {
    val purchases = List.fill(n)(buyCoffee(cc))
    val (coffee, charges) = purchases.unzip
    (coffee, charges.reduce(_.combine(_)))
  }
}

object Cafe {
  def main(args: Array[String]): Unit = {
    val cafe2 = new Cafe2()
    val cc = new CreditCard("jongsoo.lee")
    val (coffees, charges) = cafe2.buyCoffee(cc, 10)
    println(s"${coffees.size}, ${charges.amount}")
  }
}
