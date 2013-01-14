import play.api.GlobalSettings

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
object Global extends GlobalSettings {

    import play.api._
    import play.api.mvc._

    import play.api.db.DB
    import org.squeryl.{SessionFactory,Session}
    import org.squeryl.adapters.PostgreSqlAdapter

    override def onStart(app: Application) {
        SessionFactory.concreteFactory = Some( () =>
            Session.create(DB.getConnection()(app), new PostgreSqlAdapter)
        )
    }

}
