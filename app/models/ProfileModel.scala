package models

case class ProfileModel(username: String)

object ProfileModel {
	def apply() : ProfileModel = ProfileModel("")
}
