package auth

import play.api.cache.{EhCachePlugin, Cache}
import play.api.Play.current
import play.api.Logger

import auth.core.SessionStore

object AuthSessionStore extends SessionStore[String] {

  val logger = Logger(this.getClass)

  def saveSession(sessionId: String, userId: String): String = {
    logger.debug(sessionId +" -> "+ userId)
    Cache.set(sessionId, userId)
    sessionId
  }

  def lookup(sessionId: String): Option[String] = {
    Cache.getAs[String](sessionId)
  }

  def deleteSession(sessionId: String): Boolean = {
    // Hacking around the Cache API and Ehcache...
    println("removing "+ sessionId)
    current.plugin[EhCachePlugin].map {
      ehcache =>
        ehcache.cache.remove(sessionId)
    }.getOrElse(false)
  }
}