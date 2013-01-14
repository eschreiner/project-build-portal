package models

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Project(name: String, active: Boolean, owner_id: Long) extends DbNamedEntity {

}

import Database.projectTable

object Project extends DbNamedAccess[Project] {

  val table = projectTable

}