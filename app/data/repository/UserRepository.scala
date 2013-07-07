package data.repository

import anorm._ 
import play.api.db.DB
import play.api.Play.current

import data.entities._

trait UserRepositoryComponent {
	val userRepository = new UserRepository
	
	class UserRepository {
		val table = "user"

		val user = User(1, "test", "password", "")

		def getUserById(id: Int) : Option[User] = {
			DB.withConnection { implicit connection => 
				SQL(
					s"""
						SELECT id, email, password, salt, dateCreated
						FROM $table AS u
						WHERE u.id = {id}
					"""
				).on("id" -> id)().collect {
					case Row(id: Int, email: String, password: String, salt: String, dateCreated: java.sql.Timestamp) =>
						User(id, email, password, salt)
				}.headOption
			}
		}

		def getUserByUsername(email: String) : Option[User] = {
			DB.withConnection { implicit connection => 
				SQL(
					s"""
						SELECT id, email, password, salt, dateCreated
						FROM $table AS u
						WHERE u.email = {email}
					"""
				).on("email" -> email)().collect {
					case Row(id: Int, email: String, password: String, salt: String, dateCreated: java.sql.Timestamp) =>
						User(id, email, password, salt)
				}.headOption
			}
		}
	}
}
