package models.profile

case class ProfileModel(email: Option[String])

object ProfileModel {
	def apply() : ProfileModel = ProfileModel(None)
}
