package models

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Product(name: String) extends DbNamedEntity {

}

object Product extends DbNamedAccess[Product] {

	import play.api.mvc.PathBindable

    implicit def pathBinder(implicit bindableLong: PathBindable[Long]) = new PathBindable[Product]() {
        override def bind(key: String, value: String): Either[String,Product] = {
            for {
                id <- bindableLong.bind(key,value).right
                product <- Product.findBy(id).toRight("Product not found").right
            } yield product
        }
        override def unbind(key: String, product: Product): String = {
            bindableLong.unbind(key, product.id)
        }
    }

    import Database.productTable
    val table = productTable

}
