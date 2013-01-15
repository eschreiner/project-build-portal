package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.KeyedEntity
import org.squeryl.dsl.CompositeKey2

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 14, 2013 # created
 */
case class ProductUsed(product_id: Long, project_id: Long) extends KeyedEntity[CompositeKey2[Long,Long]] {
    def id = compositeKey(product_id,project_id)
}

object ProductUsed extends DbBase[CompositeKey2[Long,Long],ProductUsed] {

  import Database.productUsedTable
  val table = productUsedTable

  import org.squeryl.KeyedEntity
  import org.squeryl.PrimitiveTypeMode._
  import org.squeryl.Query
  import org.squeryl.dsl._

  def use(product: Product, project: Project) = inTransaction {
	  insert(ProductUsed(product.id,project.id))
  }

}