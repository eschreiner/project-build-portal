package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.Table
import org.squeryl.KeyedEntity

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
object Database extends Schema {

    val productTable = table[Product]("product")
    val projectTable = table[Project]("project")
    val productUsedTable = manyToManyRelation(projectTable,productTable,"product_used").
    		via[ProductUsed]((proj,prod,used) => (used.project_id === proj.id, used.product_id === prod.id))
    val milestoneTable = table[Milestone]("milestone")
    val versionTable = table[Version]("version")
    val stakeholderTable = table[Stakeholder]("stakeholder")
    val tokenTable = table[Token]("token")

    on(productTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(productTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(projectTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(milestoneTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(versionTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(stakeholderTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(tokenTable) { e => declare (
        e.id is(autoIncremented)
    )}

}

trait DbEntity extends KeyedEntity[Long] {
    val id: Long = 0
}

trait DbNamedEntity extends DbEntity {
    def name: String
}

import org.squeryl.Query
import org.squeryl.dsl._
import org.squeryl.PrimitiveTypeMode._

trait DbBase[ID,E <: KeyedEntity[ID]] {

    val table: Table[E]

    def insert(entity: E): E = inTransaction {
    	table insert entity
    }

    def updateFull(entity: E): Unit = inTransaction {
        table update entity
    }

    def singleQ(): Query[E] = from(table) (
        entity =>
            select(entity)
    )

    def single(): Option[E] = inTransaction {
        singleQ headOption
    }

}

trait DbAccess[E <: DbEntity] extends DbBase[Long,E] {

    def findBy(id: Long): Option[E] = inTransaction {
        table lookup id
    }

    def allCQ(): Long = from(table) (
        entity =>
            compute(countDistinct(entity.id))
    )

    def count(): Long = inTransaction {
        allCQ
    }

}

trait DbNamedAccess[E <: DbNamedEntity] extends DbAccess[E] {

    def allQ(): Query[E] = from(table) (
        entity =>
            select(entity)
            orderBy(entity.name)
    )

    def list(): List[E] = inTransaction {
        allQ toList
    }

    def updateName(id: Long, name: String) = inTransaction {
        update(table)(entity =>
            where(entity.id === id)
            set(entity.name := name)
        )
//        cache(id,null)
    }

}
