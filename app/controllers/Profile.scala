package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import components._
import models.profile._
import views._

object Profile extends Controller with Secured with ProvidesHeader {
	def index = IsAuthenticated { username => implicit request =>
		Ok(views.html.profile.profile(ProfileModel("", "")))
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
		Ok(html.profile.register(RegisterModel()))
	}

	def performRegistration = Action { implicit request =>
		val form = LoginForm.registerForm

		form.bindFromRequest.fold(
			errors => BadRequest(html.profile.register(errors.get)),
			data => Redirect(routes.Application.index).withSession(SessionKeys.email -> data.email)
		)
	}

	object LoginForm {
		val userService = UserComponentRegistry.userService

		val loginForm = Form(
			mapping(
				"email" -> text, 
				"password" -> text
			) (ProfileModel.apply)(ProfileModel.unapply)
			verifying ("Invalid username or password", result => result match {
				case ProfileModel(email, password) => userService.authenticate(email, password)
			})
		) 

		def registerForm = Form(
			mapping(
				"email" -> email,
				"password" -> nonEmptyText(minLength = 6),
				"passwordVerify" -> nonEmptyText(minLength = 6)
			) (RegisterModel.apply)(RegisterModel.unapply)
			verifying("Passwords do not match", result => result.password == result.passwordVerify)
		)
	}
}
