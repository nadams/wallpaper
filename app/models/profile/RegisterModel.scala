package models.profile

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
	miscErrors: Seq[String]
)

object RegisterModelErrors {
	implicit val writesRegisterModelErrors = Json.writes[RegisterModelErrors]
}
