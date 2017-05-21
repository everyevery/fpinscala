package ch01

/**
  * Created by pi on 2017. 5. 6..
  */
case class Charge(cc: CreditCard, amount: Int) {
  def combine(other: Charge): Charge = ???
}

object Charge {
  def coalesce(charges: List[Charge]): List[Charge] = ???
}
