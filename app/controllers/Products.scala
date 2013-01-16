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

    def list() = ActionWithContext { implicit context =>
        Ok(products.list(Product.list))
    }

    import play.api.data.Form
    import play.api.data.Forms._

    private val productAddForm = Form(
    		"name" -> nonEmptyText
    )

    def add() = ActionWithContext { implicit context =>
    	productAddForm.bindFromRequest.fold(
    	        hasErrors => {
    	        	Redirect(routes.Products.list)
    	        },
    	        success = { name => {
                	val result = for { user <- context.user
                	} yield {
                	    println("creating product "+ name +" for "+ user.name)
    	                val product = Product.insert(Product(name))
                	    Redirect(routes.Products.show(product))
    	            }
                	result.get
    	        }}
    	)
    }

    def updateName() = ActionWithContext { implicit context =>
        val (id,name) = nameForm.bindFromRequest.get
        Product.updateName(id,name)
        Accepted("title saved")
    }

}