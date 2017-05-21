package ch06

/**
  * Created by pi on 2017. 5. 21..
  */
trait RNG {
  def nextInt: (Int, RNG)

}
