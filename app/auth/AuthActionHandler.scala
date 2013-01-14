package auth

import play.api.Logger
import play.api.mvc.{Result, Request, Action}

import controllers.Stakeholders
import models.Stakeholder

import auth.core._

/**
 * An action handler with all the dependencies resolved.
 */
object AuthActionHandler extends ActionHandler[String, Stakeholder] {

  lazy val logger = Logger(this.getClass)

  lazy val authenticationService = AuthService

  lazy val userService = models.Stakeholder

  lazy val sessionStore = AuthSessionStore

  lazy val contextConverter = new ContextConverter[Stakeholder] {
    def apply[A](request: Request[A], user: Option[Stakeholder]) = util.Context[A](request,user)
  }

  lazy val userIdConverter = new UserIdConverter[String] {
    def apply(userId: String) = userId
  }

  def apply[A](action: Action[A]): Action[A] = {
    actionHandler(action)
  }

  def gotoSuspiciousAuthDetected[A](request: Request[A]) : Result = {
    Stakeholders.suspiciousActivity(request)
  }

  def userID(user: Stakeholder): String = user.password

}
