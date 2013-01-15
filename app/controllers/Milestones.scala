package controllers

import play.api.mvc._
import play.mvc.Http

/**
 * @author  Dr. Erich W. Schreiner - 8SPE - Rohde&amp;Schwarz
 * @version 5.6.1.0
 * @since   5.6.1.0
 * @history
 *          5.6.1.0 # Jan 15, 2013 # created
 */
object Milestones extends Controller {

    import ControllerHelpers._
    import models._
    import views.html._

    def show(milestone: Milestone) = ActionWithContext { implicit context =>
        Ok(milestones.details(milestone))
    }

    import play.api.data.Form
    import play.api.data.Forms._

    private val milestoneAddForm = Form(
        tuple(
    		"name" -> nonEmptyText,
    		"project_id" -> longNumber
    	)
    )

    def add() = ActionWithContext { implicit context =>
    	milestoneAddForm.bindFromRequest.fold(
    			hasErrors => {
    				Redirect(routes.Projects.list())
    			},
    			success = { form => {
    				form match {
    				case (name,project_id) => {
    					val result = for { project <- Project.findBy(project_id)
    					} yield {
    						println("creating milestone "+ name +" for "+ project.name)
    						Milestone.insert(Milestone(name, project_id))
    						Redirect(routes.Projects.show(project))
    					}
    					result.get
    				}
    				}
    			}
    			}
    	)
    }

    def updateName() = ActionWithContext { implicit context =>
        val (id,name) = nameForm.bindFromRequest.get
        Milestone.updateName(id,name)
        Accepted("name saved")
    }

    val deadlineForm = Form(
            tuple(
            		"id" -> longNumber,
                    "deadline" -> text
            )
    )

    import java.text.SimpleDateFormat

    def updateDeadline() = ActionWithContext { implicit context =>
        deadlineForm.bindFromRequest.fold(
            hasErrors => {
              Accepted("error updating deadline")
            },
            success = { form => {
              form match {
                case (id,date) => {
                	val deadline = new SimpleDateFormat("yyyy-MM-dd").parse(date)
                	Milestone.updateDeadline(id,deadline)
                	Accepted("deadline saved")
                }
              }
            }}
        )
    }

}
