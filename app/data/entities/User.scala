package data.entities

case class User(
	id: Int, 
	email: String, 
	password: String, 
	salt: String
)
