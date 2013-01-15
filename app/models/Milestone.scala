package models

import java.util.Date

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 15, 2013 # created
 */
case class Milestone(name: String, project_id: Long, deadline: Date, active: Boolean = false) extends DbNamedEntity {

}

object Milestone extends DbAccess[Milestone] {

  import Database.milestoneTable
  val table = milestoneTable

}