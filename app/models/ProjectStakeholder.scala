package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.KeyedEntity
import org.squeryl.dsl.CompositeKey2

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 14, 2013 # created
 */
case class ProjectStakeholder(project_id: Long, stakeholder_id: Long) extends KeyedEntity[CompositeKey2[Long,Long]] {
    def id = compositeKey(project_id,stakeholder_id)
}

object ProjectStakeholder extends DbBase[CompositeKey2[Long,Long],ProjectStakeholder] {

  import Database.projectStakeholderTable
  val table = projectStakeholderTable

  import org.squeryl.KeyedEntity
  import org.squeryl.PrimitiveTypeMode._
  import org.squeryl.Query
  import org.squeryl.dsl._

  def join(project: Project, stakeholder: Stakeholder) = inTransaction {
	  insert(ProjectStakeholder(project.id, stakeholder.id))
  }

}
