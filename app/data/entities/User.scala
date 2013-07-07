package data.entities

case class User(
	id: Long, 
	email: String, 
	password: String, 
	salt: String
)
