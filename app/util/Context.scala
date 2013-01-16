package util

import play.api.mvc.WrappedRequest
import play.api.mvc.Request

import auth.core.AuthContext
import models._

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Context[A](request: Request[A],
        user: Option[Stakeholder], project: Option[Project])
extends WrappedRequest(request) with AuthContext[A,Stakeholder]

object Context {
    def apply(context: Context[_], project: Option[Project]) = new Context(context.request,context.user,project)
}