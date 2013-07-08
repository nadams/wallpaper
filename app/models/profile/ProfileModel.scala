package models.profile

import org.joda.time.DateTime

case class ProfileModel(email: String, password: String)

case class ProfileViewModel(id: Long, email: String, password: String, dateCreated: DateTime)
