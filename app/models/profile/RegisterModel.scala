package models.profile

import play.api.libs.json.Json

case class RegisterModel(
	email: String, 
	password: String, 
	passwordVerify: String,
	errors: Seq[String]
)

object RegisterModel {
	implicit val readsRegisterModel = Json.format[RegisterModel]
	implicit val writesRegisterModel = Json.writes[RegisterModel]
}

object RegisterModelMapper {
	def create = RegisterModel("", "", "", Seq())
}