package models.profile

import play.api.data._
import play.api.data.Forms._

import components.UserComponentRegistry

object ProfileForms {
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

	val registerForm = Form(
		mapping(
			"email" -> email.verifying("error.emailTaken", userService.userDoesNotExist(_)),
			"password" -> nonEmptyText(minLength = 6),
			"passwordVerify" -> nonEmptyText(minLength = 6)
		) (RegisterModel.apply)(RegisterModel.unapply)
		verifying("error.passwordsDoNotMatch", result => result.password == result.passwordVerify)
	)
}
