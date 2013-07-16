package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n._
import components._
import models.profile._
import views._

object Profile extends Controller with Secured with ProvidesHeader {
	val userService = UserComponentRegistry.userService

	def index = IsAuthenticated { username => implicit request =>
		val user = userService.getUserByUsername(username).get

		Ok(views.html.profile.profile(ProfileViewModel(user.id, user.email, user.password, user.dateCreated)))
	}

	def login = Action { implicit request =>
		Ok(views.html.profile.login(ProfileModel("", "")))
	}

	def logout = Action { implicit request =>
		Redirect(routes.Application.index).withNewSession
	}

	def performLogin = Action { implicit request =>
		val form = LoginForm.loginForm

		form.bindFromRequest.fold(
			errors => BadRequest(html.profile.login(form.get)),
			user => Redirect(routes.Profile.index).withSession(SessionKeys.email -> user.email)
		)
	}

	def register = Action { implicit request =>
		Ok(
			html.profile.register(
				RegisterModel("", "", "")
		))
	}

	def performRegistration = Action { implicit request =>
		val form = LoginForm.registerForm

		form.bindFromRequest.fold(
			errors => {
				def getErrorMessage(field: Field) : Tuple2[String, Option[String]] = 
					(field.value.getOrElse(""), field.error.map { error => Messages(error.message, error.args: _*) })

				val email = getErrorMessage(errors("email"))
				val password = getErrorMessage(errors("password"))
				val passwordVerify = getErrorMessage(errors("passwordVerify"))

				val errorModel = RegisterModelErrors(email._2, password._2, passwordVerify._2)
				val model = RegisterModel(email._1, "", "")
				BadRequest(html.profile.register(model, Some(errorModel))) 
			},
			data => userService.createNewUser(data.email, data.password) match {
				case Some(x) => Redirect(routes.Application.index).withSession(SessionKeys.email -> x.email)
				case None => InternalServerError("Could not register user")
			}
		)
	}

	object LoginForm {
		val userService = UserComponentRegistry.userService

		val loginForm = Form(
			mapping(
				"email" -> text, 
				"password" -> text
			) (ProfileModel.apply)(ProfileModel.unapply)
			verifying ("error.invalidUsernamePassword", result => result match {
				case ProfileModel(email, password) => userService.authenticate(email, password)
			})
		)  	 	

		def registerForm = Form(
			mapping(
				"email" -> email.verifying("error.emailTaken", userService.userDoesNotExist(_)),
				"password" -> nonEmptyText(minLength = 6),
				"passwordVerify" -> nonEmptyText(minLength = 6)
			) (RegisterModel.apply)(RegisterModel.unapply)
			verifying("error.passwordsDoNotMatch", result => result.password == result.passwordVerify)
		)
	}
}
