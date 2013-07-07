package models.profile

case class RegisterModel(
	username: Option[String], 
	password: Option[String], 
	passwordVerify: Option[String]
)

object RegisterModel {
	def apply() : RegisterModel = RegisterModel(None, None, None)
}
