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
object Projects extends Controller {

    import ControllerHelpers._
    import models._
    import views.html._

    def show(project: Project) = ActionWithContext { implicit context =>
        Ok(projects.details(project))
    }

    import java.text.SimpleDateFormat
    import java.util.Date

    def list() = ActionWithContext { implicit context =>
        Ok(projects.list(Project.list))
    }

    import play.api.data.Form
    import play.api.data.Forms._

    private val projectAddForm = Form(
    		"name" -> nonEmptyText
    )

    def add() = ActionWithContext { implicit context =>
    	projectAddForm.bindFromRequest.fold(
    	        hasErrors => {},
    	        success = { name => {
                	for { user <- context.user
                	} yield {
                	    println("creating project "+ name +" for "+ user.name)
    	                val project = Project.insert(new Project(name, user.id))
    	                if (Product.count() == 1) {
    	                    for (product <- Product.single()) {
    	                    	ProductUsed.use(product,project)
    	                    }
    	                }
    	            }
    	        }}
    	)
    	Redirect(routes.Projects.list)
    }

    def updateName() = ActionWithContext { implicit context =>
        val (id,name) = nameForm.bindFromRequest.get
        Project.updateName(id,name)
        Accepted("title saved at "+ new SimpleDateFormat("HH:mm:ss").format(new Date()))
    }

}