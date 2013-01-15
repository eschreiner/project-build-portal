package models

import java.util.Date

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 15, 2013 # created
 */
case class Milestone(name: String, project_id: Long, deadline: Option[Date] = None, active: Boolean = false) extends DbNamedEntity {

}

object Milestone extends DbNamedAccess[Milestone] {

	import play.api.mvc.PathBindable

    implicit def pathBinder(implicit bindableLong: PathBindable[Long]) = new PathBindable[Milestone]() {
        override def bind(key: String, value: String): Either[String,Milestone] = {
            for {
                id <- bindableLong.bind(key,value).right
                milestone <- Milestone.findBy(id).toRight("Milestone not found").right
            } yield milestone
        }
        override def unbind(key: String, milestone: Milestone): String = {
            bindableLong.unbind(key, milestone.id)
        }
    }

  import Database.milestoneTable
  val table = milestoneTable

    import org.squeryl.PrimitiveTypeMode._
    import org.squeryl.Query
    import org.squeryl.annotations.Column
    import org.squeryl.dsl._

    def forProjectQ(projectID: Long): Query[Milestone] = from(table) (
        milestone =>
            where(milestone.project_id === projectID)
            select(milestone)
            orderBy(milestone.name)
    )

  def listFor(project: Project) = inTransaction {
    forProjectQ(project.id) toList
  }

}