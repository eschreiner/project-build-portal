package models

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Project(name: String, active: Boolean, owner_id: Long) extends DbNamedEntity {

}

object Project extends DbNamedAccess[Project] {

	import play.api.mvc.PathBindable

    implicit def pathBinder(implicit bindableLong: PathBindable[Long]) = new PathBindable[Project]() {
        override def bind(key: String, value: String): Either[String,Project] = {
            for {
                id <- bindableLong.bind(key,value).right
                project <- Project.findBy(id).toRight("Project not found").right
            } yield project
        }
        override def unbind(key: String, project: Project): String = {
            bindableLong.unbind(key, project.id)
        }
    }

	import Database.projectTable
	val table = projectTable

    import org.squeryl.PrimitiveTypeMode._
    import org.squeryl.Query
    import org.squeryl.annotations.Column
    import org.squeryl.dsl._

    def forUserQ(userID: Long): Query[Project] = from(table) (
        project =>
            where(project.owner_id === userID)
            select(project)
            orderBy(project.name)
    )

    def listFor(user: Stakeholder): List[Project] = inTransaction {
        forUserQ(user.id).toList
    }

}
