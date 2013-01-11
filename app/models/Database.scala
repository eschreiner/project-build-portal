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

    val projectTable = table[Project]("project")

    on(projectTable) { e => declare (
        e.id is(autoIncremented)
    )}

}

trait PdpDbEntity extends KeyedEntity[Long] {
    val id: Long = 0
}