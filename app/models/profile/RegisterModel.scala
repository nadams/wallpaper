package models.profile

import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

case class RegisterModel(
	email: String, 
	password: String, 
	passwordVerify: String
)

object RegisterModel {
	implicit val writesRegisterModel = Json.writes[RegisterModel]
}

case class RegisterModelErrors(
	usernameError: Option[String],
	passwordError: Option[String],
	passwordVerifyError: Option[String],
	formErrors: Seq[String]
)

object RegisterModelErrors {
	implicit val writesRegisterModelErrors = Json.writes[RegisterModelErrors]
}

object RegisterForm {
	val userService = components.UserComponentRegistry.userService

	def apply() = Form(
		mapping(
			"email" -> email.verifying("error.emailTaken", userService.userDoesNotExist(_)),
			"password" -> nonEmptyText(minLength = 6),
			"passwordVerify" -> nonEmptyText(minLength = 6)
		) (RegisterModel.apply)(RegisterModel.unapply)
		verifying("error.passwordsDoNotMatch", result => result.password == result.passwordVerify)
	)
}
