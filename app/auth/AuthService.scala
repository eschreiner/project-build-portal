package auth

import auth.core._

import play.api.Logger
import models.{Token, Stakeholder}

import scala.util.Random

object AuthService extends AuthenticationService[String,Stakeholder] {

  private lazy val logger = Logger("models.AuthService")

  /**
   * Authenticate a User.
   */
  def authenticate(email: String, password: String, rememberMe: Boolean): Either[AuthenticationFault, UserAuthenticatedEvent[Stakeholder]] = {
    logger.debug("authenticate")
    Stakeholder.authenticate(email, password).map {
      user =>
      // When the user successfully logs in with Remember Me checked, a login cookie is issued in addition to the
      // standard session management cookie.[2]
        logger.debug("authenticate: right, rememberMe = " + rememberMe)
        val token = rememberMe match {
          case true => {
            val series = Random.nextLong()
            val token = Random.nextLong()
            val sessionToken = Token(user.password, series, token)
            Option(Token.insert(sessionToken))
          }
          case false => None
        }
        logger.info("authenticate: token = " + token)
        val event = UserAuthenticatedEvent(user, token.map(_.series), token.map(_.token))
        Right(event)
    }.getOrElse(Left(InvalidCredentialsFault()))
  }

  def authenticateWithCookie(userId: String, series: Long, token: Long): Either[AuthenticationFault, UserAuthenticatedWithTokenEvent[Stakeholder]] = {

    // When a non-logged-in user visits the site and presents a login cookie, the username, series, and token are
    // looked up in the database.
    Token.find(userId, series).map {
      rememberMeToken =>
      // If the triplet is present, the user is considered authenticated.
        if (rememberMeToken.token == token) {
          // The used token is removed from the database.
          Token.delete(rememberMeToken)
          // A new token is generated, stored in database with the username and the same series identifier, and a new login
          // cookie containing all three is issued to the user.
          val newToken = Token(userId, series, Random.nextLong())
          Token.insert(newToken)
          val user = Stakeholder.recoverFromToken(userId)
          user map (u => Right(UserAuthenticatedWithTokenEvent(u, newToken.series, newToken.token))) getOrElse {
        	  Token.deleteFor(userId)
        	  Left(InvalidSessionCookieFault())
          }
        } else {
          // If the username and series are present but the token does not match, a theft is assumed. The user receives
          // a strongly worded warning and all of the user's remembered sessions are deleted.
          Token.deleteFor(userId)
          Left(InvalidSessionCookieFault())
        }
    }.getOrElse(Left(InvalidCredentialsFault()))
  }

}
