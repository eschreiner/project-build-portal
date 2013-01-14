package auth.core

trait SessionStore[UserID] {

  def deleteSession(sessionId: String) : Boolean

  def saveSession(sessionId: String, userId: UserID): String

  def lookup(sessionId: String) : Option[UserID]

}
