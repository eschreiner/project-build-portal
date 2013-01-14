package auth.core

trait AuthenticationEvent

case class UserAuthenticatedEvent[UserInfo](userInfo: UserInfo, series: Option[Long] = None, token: Option[Long] = None)
extends AuthenticationEvent

case class UserAuthenticatedWithTokenEvent[UserInfo](userInfo: UserInfo, series: Long, token: Long)
extends AuthenticationEvent

trait AuthenticationFault

case class InvalidSessionCookieFault() extends AuthenticationFault

case class InvalidCredentialsFault() extends AuthenticationFault
