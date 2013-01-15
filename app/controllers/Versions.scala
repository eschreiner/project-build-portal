package controllers

import play.api.mvc._
import play.mvc.Http

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 15, 2013 # created
 */
object Versions extends Controller {

    import ControllerHelpers._
    import models._
    import views.html._

    def show(version: Version) = ActionWithContext { implicit context =>
        Ok(versions.details(version))
    }

    import play.api.data.Form
    import play.api.data.Forms._

    private val versionAddForm = Form(
        tuple(
    		"name" -> nonEmptyText,
    		"product_id" -> longNumber
    	)
    )

    def add() = ActionWithContext { implicit context =>
    	versionAddForm.bindFromRequest.fold(
    			hasErrors => {
    				Redirect(routes.Products.list())
    			},
    			success = { form => {
    				form match {
    				case (name,product_id) => {
    					val result = for { product <- Product.findBy(product_id)
    					} yield {
    						println("creating version "+ name +" for "+ product.name)
    						val version = Version.insert(Version(name, product_id))
    						Redirect(routes.Versions.show(version))
    					}
    					result.get
    				}
    				}
    			}
    			}
    	)
    }

    def updateName() = ActionWithContext { implicit context =>
        val (id,name) = nameForm.bindFromRequest.get
        Version.updateName(id,name)
        Accepted("name saved")
    }

//    val deadlineForm = Form(
//            tuple(
//            		"id" -> longNumber,
//                    "deadline" -> text
//            )
//    )
//
//    import java.text.SimpleDateFormat
//
//    def updateDeadline() = ActionWithContext { implicit context =>
//        deadlineForm.bindFromRequest.fold(
//            hasErrors => {
//              Accepted("error updating deadline")
//            },
//            success = { form => {
//              form match {
//                case (id,date) => {
//                	val deadline = new SimpleDateFormat("yyyy-MM-dd").parse(date)
//                	Milestone.updateDeadline(id,deadline)
//                	Accepted("deadline saved")
//                }
//              }
//            }}
//        )
//    }

}