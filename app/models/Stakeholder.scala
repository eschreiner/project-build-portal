package models

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Stakeholder(name: String, fullname: String, email: String) extends DbNamedEntity {

  import org.apache.commons.codec.digest.DigestUtils.md5Hex
  def gravatar(): String = md5Hex(email.toLowerCase().trim())

}

import Database.stakeholderTable

object Stakeholder extends DbNamedAccess[Stakeholder] {

  val table = stakeholderTable

}