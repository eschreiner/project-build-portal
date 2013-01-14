package models

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Stakeholder(name: String, email: String, password: String, salt: String) extends DbNamedEntity {

	import Stakeholder.hashFor
	def verify(password: String): Boolean = hashFor(password,salt).equals(this.password)

	import org.apache.commons.codec.digest.DigestUtils.md5Hex
	def gravatar(): String = md5Hex(email.toLowerCase().trim())

	def sessionCookie(client: String): String = {
		password +":"+ checkFor(client)
	}
	def checkFor(client: String) = md5Hex((salt+client).toLowerCase()).toUpperCase()
}

object Stakeholder extends DbNamedAccess[Stakeholder] with auth.core.UserInfoService[String,Stakeholder]  {

	import org.squeryl.KeyedEntity
	import org.squeryl.PrimitiveTypeMode._
	import org.squeryl.Query
	import org.squeryl.annotations.Column
	import org.squeryl.dsl._

	import Database.stakeholderTable
	val table = stakeholderTable

	import util._

    def apply(name: String, email: String, password: String) = {
        val salt = MD5Helper(RandomString(100))
    	new Stakeholder(name,email,hashFor(password,salt),salt)
    }

	def hashFor(password: String, salt: String) = SHA1Helper(salt + password)

	def recoverFrom(cookie: Option[String], client: String): Option[Stakeholder] = inTransaction {
        cookie flatMap (c => {
            if (c.contains(":")) {
            	val Array(token,check) = c.split(":",2)
            	recoverQ(token).headOption filter (a => a.checkFor(client) == check)
            } else {
                println("wrong cookie format: "+c)
                None
            }
        })
    }

    def recoverFromToken(token: String): Option[Stakeholder] = inTransaction {
        recoverQ(token).headOption
    }

	private def recoverQ(cookie: String): Query[Stakeholder] = from(table) (
			account =>
			where(account.password === cookie)
			select(account)
    )

    def authenticate(name: String, password: String): Option[Stakeholder] = {
        for (account <- lookupBy(name); if (passwordOK(account,password))) yield account
    }

    def lookup(name: String) = lookupBy(name)

    private def lookupBy(name: String): Option[Stakeholder] = inTransaction {
        lookupQ(name).headOption
    }

    private def passwordOK(account: Stakeholder, password: String) =
    	account.password == hashFor(password,account.salt)

    private def lookupQ(name: String): Query[Stakeholder] = from(table) (
            account =>
                where(account.name === name or account.email === name)
                select(account)
    )

}
