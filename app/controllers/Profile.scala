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
			user => Redirect(routes.Profile.index).withSession(SessionKeys.email -> user._1)
		)
	}

	def register = Action { implicit request =>
		Ok(html.profile.register(RegisterModel()))
	}

	def performRegistration = Action { implicit request =>
		val form = LoginForm.registerForm

		Ok("")
	}

	object LoginForm {
		val userService = UserComponentRegistry.userService

		val loginForm = Form(
			tuple(
				"email" -> text, 
				"password" -> text
			) verifying ("Invalid username or password", result => result match {
				case (email, password) => userService.authenticate(email, password)
			})
		) 

		def loginFormToProfileModel(form: Form[(String, String)]) = ProfileModel()

		def registerForm = Form(
			tuple(
				"email" -> text,
				"password" -> text,
				"verifyPassword" -> text
			)
		)
	}
}
