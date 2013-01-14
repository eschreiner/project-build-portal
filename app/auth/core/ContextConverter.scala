package auth.core

import play.api.mvc.Request

trait ContextConverter[UserInfo] {

  def apply[A](request: Request[A], user: Option[UserInfo]): AuthContext[A,UserInfo]

}
