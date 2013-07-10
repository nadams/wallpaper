package models.profile

import org.joda.time.DateTime
import play.api.libs.json.Json

case class ProfileModel(email: String, password: String)

case class ProfileViewModel(id: Long, email: String, password: String, dateCreated: DateTime)

object ProfileViewModel {
	implicit val readsProfileViewModel = Json.format[ProfileViewModel]
	implicit val writesProfileViewModel = Json.writes[ProfileViewModel]
}
