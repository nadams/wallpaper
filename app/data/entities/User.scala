package data.entities

import org.joda.time.{ DateTime, DateTimeZone, Chronology }

case class User(
	id: Long, 
	email: String, 
	password: String,
	dateCreated: DateTime = DateTime.now.toDateTime(DateTimeZone.UTC)
)
