package models.profile

case class RegisterModel(
	email: Option[String], 
	password: Option[String], 
	passwordVerify: Option[String]
)

object RegisterModel {
	def apply() : RegisterModel = RegisterModel(None, None, None)
}
