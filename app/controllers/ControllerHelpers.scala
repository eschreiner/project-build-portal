package controllers

import play.api.mvc._

import util.Context

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
object ControllerHelpers {

    import play.api.http.HeaderNames._

    import models._

    def ActionWithContext(f: Context[AnyContent] => PlainResult) = {
        Action { request =>
            val cookie = request.session.get("session")
            val account = Stakeholder.recoverFrom(cookie,request.remoteAddress)
            val project = Project.recoverFrom(request.session.get("project"))
            val result = f(Context(request,account,project))
            result.withHeaders(CACHE_CONTROL -> "no-cache")
        }
    }

    import play.api.data.Form
    import play.api.data.Forms._

    val nameForm = Form(
            tuple(
            		"id" -> longNumber,
                    "name" -> text
            )
    )

}