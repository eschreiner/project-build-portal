package models

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 15, 2013 # created
 */
case class Version(name: String, project_id: Long, tag: String = "dev") extends DbNamedEntity {

}

object Version extends DbAccess[Version] {

  import Database.versionTable
  val table = versionTable

}