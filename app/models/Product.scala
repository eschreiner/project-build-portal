package models

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Product(name: String) extends DbNamedEntity {

}

import Database.productTable

object Product extends DbNamedAccess[Product] {

  val table = productTable

}
