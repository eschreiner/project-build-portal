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
object Stakeholders extends Controller with SessionSaver[String] {

    import ControllerHelpers._
    import auth._
    import models._
    import views.html._

    import play.api.Logger
    import play.api.data.Form
    import play.api.data.Forms._
    import play.api.i18n.Messages

    val logger = Logger(this.getClass)

    def authenticationService = AuthService
    def sessionStore = AuthSessionStore

    private val registrationForm = Form(
            tuple(
                    "account" -> nonEmptyText(minLength=3),
                    "email" -> nonEmptyText,
                    "password1" -> nonEmptyText(minLength=6),
                    "password2" -> nonEmptyText,
                    "acceptTNC" -> checked(Messages("register.error.accept.TNC"))
            ) verifying(Messages("register.error.password.mismatch"), result => result match {
                case (_,_,p1,p2,_) => p1 == p2
			})
    )
    private val loginForm = Form(
            mapping(
                    "account" -> nonEmptyText,
                    "password" -> text,
                    "rememberMe" -> boolean
            )(authenticateUser)(_ => None)
            	.verifying(Messages("login.invalid"), _.isDefined
            )
    )

    def showRegistration = ActionWithContext { implicit context =>
        val form = if (flash.get("error").isDefined)
            registrationForm.bind(flash.data)
        else
            registrationForm
        Ok(stakeholders.register(form))
    }

    def register = ActionWithContext { implicit context =>
        registrationForm.bindFromRequest.fold(
                hasErrors = { form =>
//                    println(form.errorsAsJson(Lang.defaultLang))
                    Redirect(routes.Stakeholders.showRegistration).
                    	flashing(Flash(form.data) +
                            ("error" -> Messages("validation.errors")))
                },
                success = { form =>
                    val ao = form match {
                    	case (name,email,password,_,_) => {
                    		Stakeholder.insert(Stakeholder(name,email,password))
                    	}
                    }
                    Redirect(routes.Projects.list()).withSession("session" -> ao.password)
                }
        )
    }

    def login = ActionWithContext { implicit context =>
        val form = if (flash.get("error").isDefined)
            loginForm.bind(flash.data)
        else
            loginForm
        Ok(stakeholders.login(form))
    }

    def authenticate = ActionWithContext { implicit context =>
        loginForm.bindFromRequest.fold(
                errors => {
                	logger.debug("Bad request: err = " + errors)
                	authorizationFailed()
                },
                success => {
                    success.map {
                    	e => gotoLoginSucceeded(e.userInfo,e.series,e.token)
                    }.getOrElse {
                       authorizationFailed()
                    }
                }
        )
    }

    def authenticateUser(name: String, password: String, rememberMe: Boolean):
    			Option[UserAuthenticatedEvent[Stakeholder]] = {
    	authenticationService.authenticate(name, password, rememberMe).fold(
    		fault => {
    			logger.debug("authenticateUser: failed")
    			None
    		},
    		event => Some(event)
    	)
    }

    def authorizationFailed(): PlainResult =
    	Redirect(routes.Stakeholders.login).
    		discardingCookies (RememberMe.COOKIE_NAME).
    		flashing("error" -> Messages("validation.errors"))

    def logout = ActionWithContext { implicit context =>
        logger.info("logout")
    	context.request.session.get("sessionId") foreach {
    		sessionStore.deleteSession(_)
    	}
    	Redirect(routes.Projects.list).
    		withNewSession.
    		discardingCookies (RememberMe.COOKIE_NAME).
        	flashing("success" -> Messages("logout.success"))
    }

    def suspiciousActivity(implicit req: RequestHeader): PlainResult = {
    		Redirect(routes.Stakeholders.suspicious())
    }

    def suspicious = ActionWithContext { implicit context =>
    	Ok(stakeholders.suspicious())
    }

    def gotoLoginSucceeded[A](user: Stakeholder, series: Option[Long], token: Option[Long])(implicit req: RequestHeader) = {
    	logger.debug("gotoLoginSucceeded: login succeeded, userId = " + user.id)
    	val sessionId = saveAuthentication(user.password)
    	val sessionCookie = SessionCookie("sessionId", sessionId)

    	val rememberMeCookie = for {
    		s <- series
    		t <- token
    	} yield {
    		val rememberMe = RememberMe(user.password, s, t)
    		RememberMe.encodeAsCookie(rememberMe)
    	}

    	// Append a session cookie here.
    	val cookies: Seq[Cookie] = rememberMeCookie.toList
    	loginSucceeded(req).
    			flashing("success" -> Messages("login.success")).
    			withCookies (cookies: _*).
    			withSession (session + ("sessionId" -> sessionId) + ("session" -> user.sessionCookie(req.remoteAddress)))
    }

    def loginSucceeded(req: RequestHeader): PlainResult = {
    	val uri = req.session.get("access_uri").getOrElse(routes.Projects.list().url)
    	req.session - "access_uri"
    	Redirect(uri)
    }

}
