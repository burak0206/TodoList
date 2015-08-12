package controllers

import com.google.inject.Inject
import models.Task
import play.api.data._
import play.api.data.Forms._
import play.api.i18n._
import play.api.mvc._

/**
 * Created by burakdagli on 12.08.15.
 */
class ApplicationController @Inject()(val messagesApi: MessagesApi)  extends Controller with I18nSupport {

  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index = Action {
    //Ok(views.html.index("Your new application is ready."))
    Redirect(routes.ApplicationController.tasks)
    //Ok(views.html.hello(Task(1,"burak")::Nil,taskForm))
  }
  def tasks = Action {
    Ok(views.html.hello(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.hello(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.ApplicationController.tasks)
      }
    )
 }

  def deleteTask(id: Long) = Action {  request =>
    Task.delete(id)
    Redirect(routes.ApplicationController.tasks)
  }

}
