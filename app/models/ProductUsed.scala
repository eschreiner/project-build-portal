package models

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 14, 2013 # created
 */
case class ProductUsed(product_id: Long, project_id: Long) extends DbEntity {

}

object ProductUsed extends DbAccess[ProductUsed] {

  import Database.productUsedTable
  val table = productUsedTable

  import org.squeryl.KeyedEntity
  import org.squeryl.PrimitiveTypeMode._
  import org.squeryl.Query
  import org.squeryl.dsl._

  def use(product_id: Long, project_id: Long) = inTransaction {
	  insert(ProductUsed(product_id,project_id))
  }

}