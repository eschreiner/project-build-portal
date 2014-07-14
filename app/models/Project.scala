package models

import org.squeryl.PrimitiveTypeMode._

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Project(name: String, owner_id: Long, dormant: Boolean = false, milestone_id: Option[Long] = None) extends DbNamedEntity {

    import models.Database.productUsedTable
    import models.Database.projectStakeholderTable

    lazy val products = productUsedTable.left(this)
    def productList(): Seq[Product] = inTransaction {
        products.toList
    }

    lazy val stakeholders = projectStakeholderTable.left(this)
    def stakeholderList(): Seq[Stakeholder] = inTransaction {
        stakeholders.toList
    }
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

	def recoverFrom(cookie: Option[String]): Option[Project] = inTransaction {
        cookie flatMap (c => {
        	recoverQ(c).headOption
        })
    }

	private def recoverQ(cookie: String): Query[Project] = from(table) (
		project =>
			where(project.id === cookie.toLong)
			select(project)
    )

    def forUserQ(userID: Long): Query[Project] = from(table) (
        project =>
            where(project.owner_id === userID)
            select(project)
            orderBy(project.name)
    )

    def listFor(user: Stakeholder): List[Project] = inTransaction {
        forUserQ(user.id).toList
    }

    def activate(milestone: Milestone) = inTransaction {
        update(table)(entity =>
            where(entity.id === milestone.project_id)
            set(entity.milestone_id := Option(milestone.id))
        )
    }

}
