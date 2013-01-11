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
    val stakeholderTable = table[Stakeholder]("stakeholder")

    on(productTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(projectTable) { e => declare (
        e.id is(autoIncremented)
    )}
    on(stakeholderTable) { e => declare (
        e.id is(autoIncremented)
    )}

}

trait PdpDbEntity extends KeyedEntity[Long] {
    val id: Long = 0
}

trait PdpDbAccess[E <: PdpDbEntity] {

    val table: Table[E]

    def insert(entity: E): E = inTransaction {
    	table insert entity
    }

    def findBy(id: Long): Option[E] = inTransaction {
        table lookup id
    }

    def update(entity: E): Unit = inTransaction {
        table update entity
    }

}