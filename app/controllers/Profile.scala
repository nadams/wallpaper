package controllers

import play.api.mvc._
import play.api.i18n._
import components._
import models.profile._
import models.FieldExtensions.formattedMessage
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
		val form = ProfileForms.loginForm

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
		val form = ProfileForms.registerForm

		form.bindFromRequest.fold(
			errors => {
				val email = errors("email").formattedMessage
				val password = errors("password").formattedMessage
				val passwordVerify = errors("passwordVerify").formattedMessage

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
}
