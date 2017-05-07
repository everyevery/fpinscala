package ch01

/**
  * Created by pi on 2017. 5. 6..
  */
class CreditCard(owner: String) {
  def charge(price: Int): Unit = println(s"$price is charged.")
}
