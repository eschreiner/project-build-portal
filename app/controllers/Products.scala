package controllers

import play.api.mvc._
import play.mvc.Http

import auth.core._

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 14, 2013 # created
 */
object Products extends Controller {

    import ControllerHelpers._
    import models._
    import views.html._

    def show(product: Product) = ActionWithContext { implicit context =>
        Ok(products.details(product))
    }

    import java.text.SimpleDateFormat
    import java.util.Date

    def list() = ActionWithContext { implicit context =>
        Ok(products.list(Product.list))
    }
    def create() = ActionWithContext { implicit context =>
        val product = Product.insert(new Product("???"))
        Redirect(routes.Products.show(product))
    }
    def updateName() = ActionWithContext { implicit context =>
        val (id,name) = nameForm.bindFromRequest.get
        Product.updateName(id,name)
        Accepted("title saved at "+ new SimpleDateFormat("HH:mm:ss").format(new Date()))
    }

}