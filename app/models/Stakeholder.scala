package models

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Stakeholder(name: String, fullname: String, email: String) extends DbEntity {

}

import Database.stakeholderTable

object Stakeholder extends DbAccess[Stakeholder] {
  
  val table = stakeholderTable
  
}