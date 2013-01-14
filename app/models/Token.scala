package models

import play.api.cache.Cache
import play.api.Play.current
import play.api.mvc.PathBindable

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Query

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Token(user_id: String, series: Long, token: Long) extends DbEntity {}

object Token extends DbAccess[Token] {

    import models.Database.tokenTable
    val table = tokenTable

    private def findQ(user_id: String, series: Long): Query[Token] = from(tokenTable) (
    	token =>
        	where(token.user_id === user_id and token.series === series)
        	select(token)
    )

    def find(user_id: String, series: Long) = inTransaction {
        findQ(user_id,series).headOption
    }

    def delete(token: Token) = inTransaction {
        tokenTable delete token.id
    }

    def deleteFor(user_id: String) = inTransaction {
   	    tokenTable.deleteWhere(t =>
	        t.user_id === user_id
	    )
    }

}
