package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import components._
import models.profile._
import views._

object Profile extends Controller with Secured with ProvidesHeader {
	def index = IsAuthenticated { username => implicit request =>
		Ok(views.html.profile.profile(ProfileModel()))
	}

	def login = Action { implicit request =>
		Ok(views.html.profile.login(ProfileModel()))
	}

	def logout = Action { implicit request =>
		Redirect(routes.Application.index).withNewSession
	}

	def performLogin = Action { implicit request =>
		val form = LoginForm.loginForm

		form.bindFromRequest.fold(
			errors => BadRequest(html.profile.login(LoginForm.loginFormToProfileModel(form))),
			user => Redirect(routes.Profile.index).withSession("username" -> user._1)
		)
	}

	object LoginForm {
		val userService = UserComponentRegistry.userService

		val loginForm = Form(
			tuple(
				"username" -> text, 
				"password" -> text
			) verifying ("Invalid username or password", result => result match {
				case (username, password) => userService.authenticate(username, password)
			})
		) 

		def loginFormToProfileModel(form: Form[(String, String)]) = ProfileModel()
	}
}
