package models.profile

import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

case class LoginModel(email: String, password: String)

object LoginModel {
	implicit val readsLoginModel = Json.format[LoginModel]
	implicit val writesProfileModel = Json.writes[LoginModel]
}

case class LoginModelErrors(
	emailError: Option[String],
	passwordError: Option[String],
	formErrors: Seq[String]
)

object LoginModelErrors {
	implicit val writesProfileModelErrors = Json.writes[LoginModelErrors]
}

object LoginForm {
	val userService = components.UserComponentRegistry.userService

	def apply() = Form(
		mapping(
			"email" -> email, 
			"password" -> nonEmptyText(minLength = 6)
		) (ProfileModel.apply)(ProfileModel.unapply)
		verifying ("error.invalidUsernamePassword", result => result match {
			case ProfileModel(email, password) => userService.authenticate(email, password)
		})
	)
}
