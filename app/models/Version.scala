package models

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 15, 2013 # created
 */
case class Version(name: String, product_id: Long, tag: String = "dev") extends DbNamedEntity {

}

object Version extends DbNamedAccess[Version] {

	import play.api.mvc.PathBindable

    implicit def pathBinder(implicit bindableLong: PathBindable[Long]) = new PathBindable[Version]() {
        override def bind(key: String, value: String): Either[String,Version] = {
            for {
                id <- bindableLong.bind(key,value).right
                version <- Version.findBy(id).toRight("Version not found").right
            } yield version
        }
        override def unbind(key: String, version: Version): String = {
            bindableLong.unbind(key, version.id)
        }
    }

  import Database.versionTable
  val table = versionTable

    import org.squeryl.PrimitiveTypeMode._
    import org.squeryl.Query
    import org.squeryl.annotations.Column
    import org.squeryl.dsl._

    def forProductQ(productID: Long): Query[Version] = from(table) (
        version =>
            where(version.product_id === productID)
            select(version)
            orderBy(version.name desc)
    )

  def listFor(product: Product) = inTransaction {
    forProductQ(product.id) toList
  }

}
