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
    import util.Context
    import views.html._

    private def ActionWithProject(project: Option[Project])(f: Context[AnyContent] => PlainResult) = {
        Action { request =>
            val cookie = request.session.get("session")
            val account = Stakeholder.recoverFrom(cookie,request.remoteAddress)
            val result = f(Context(request,account,project))
            result.withHeaders(CACHE_CONTROL -> "no-cache")
        }
    }

    def show(project: Project) = ActionWithProject(Option(project)) { implicit context =>
        Ok(projects.details(project)).withSession(context.request.session + ("project" -> project.id.toString()))
    }

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
    	        hasErrors => {
    	        	Redirect(routes.Projects.list)
    	        },
    	        success = { name => {
                	val result = for { user <- context.user
                	} yield {
                	    println("creating project "+ name +" for "+ user.name)
    	                val project = Project.insert(new Project(name, user.id))
    	                if (Product.count() == 1) {
    	                    for (product <- Product.single()) {
    	                    	ProductUsed.use(product,project)
    	                    }
    	                }
                	    Redirect(routes.Projects.show(project))
    	            }
                	result.get
    	        }}
    	)
    }

    def updateName() = ActionWithContext { implicit context =>
        val (id,name) = nameForm.bindFromRequest.get
        Project.updateName(id,name)
        Accepted("title saved")
    }

    def listInline() = ActionWithContext { implicit context =>
        Ok(projects.listInline())
    }

    def showInline(project: Project) = ActionWithContext { implicit context =>
        Ok(projects.control(project))
    }

}