package models.profile

case class RegisterModel(
	email: String, 
	password: String, 
	passwordVerify: String
)

object RegisterModel {
	def apply() : RegisterModel = RegisterModel("", "", "")
}
