package auth.core

import play.api.mvc.{ Cookie, Session, RequestHeader }

object SessionCookie {

  def apply(name: String, value: String)(implicit req: RequestHeader): Cookie = {
    val data = req.session + (name -> value)
    val encoded = Session.encode(Session.serialize(data))
    Cookie(Session.COOKIE_NAME, encoded)
  }
}