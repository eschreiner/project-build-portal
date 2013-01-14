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
    def create() = ActionWithContext { implicit context =>
        val project = Project.insert(new Project("???", true, context.user.get.id))
        Redirect(routes.Projects.show(project))
    }
    def updateTitle() = ActionWithContext { implicit context =>
        val (id,name) = nameForm.bindFromRequest.get
        Project.updateName(id,name)
        Accepted("title saved at "+ new SimpleDateFormat("HH:mm:ss").format(new Date()))
    }

}