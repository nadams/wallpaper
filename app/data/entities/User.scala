package data.entities

import org.joda.time.{ DateTime, DateTimeZone }

case class User(
	id: Long, 
	email: String, 
	password: String,
	dateCreated: DateTime = new DateTime(DateTimeZone.UTC)
)
