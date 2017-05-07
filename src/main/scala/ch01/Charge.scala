package ch01

/**
  * Created by pi on 2017. 5. 6..
  */
case class Charge(cc: CreditCard, amount: Int) {
  def combine(other: Charge): Charge = {
    if (cc == other.cc)
      Charge(cc, this.amount + other.amount)
    else
      throw new Exception("Can't combine charges to different card")
  }
}

object Charge {
  def coalesce(charges: List[Charge]): List[Charge] = {
    charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList
  }
}
