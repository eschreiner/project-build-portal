package auth.core

import play.api.mvc.Request

trait AuthContext[A,UserInfo] extends Request[A] {

  def request: Request[A]

  def user: Option[UserInfo]

  def isAuth = user.isDefined

  def is(user: UserInfo) = this.user == Some(user)
}