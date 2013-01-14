package auth.core

import scala.util.Random

trait SessionSaver[UserID] {

  def sessionStore: SessionStore[UserID]

  def saveAuthentication(userId: UserID): String = {
    sessionStore.saveSession(Random.nextString(20), userId)
  }

}
