package data.repository

import anorm._ 
import anorm.SqlParser._
import java.sql._
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
				val query = SQL(
					s"""
						SELECT id, email, password, salt
						FROM $table AS u
						WHERE u.id = {id}
					"""
				).on("id" -> id)

				UserMapper(query)
			}
		}

		def getUserByUsername(email: String) : Option[User] = {
			DB.withConnection { implicit connection => 
				val query = SQL(
					s"""
						SELECT id, email, password, salt, dateCreated
						FROM $table AS u
						WHERE u.email = {email}
					"""
				).on("email" -> email)

				UserMapper(query)
			}
		}

		def insertUser(user: User) : Option[User] = {
			DB.withConnection { implicit connection => 
				SQL(
					s"""
						INSERT INTO $table(`email`, `password`, `salt`)
						VALUES({email}, {password}, {salt}, NOW())

						SELECT LAST_INSERT_ID()
					"""
				).on(
					"email" -> user.email,
					"password" -> user.password,
					"salt" -> user.salt
				).executeInsert()
			} match {
				case Some(id: Long) => Some(User(id, user.email, user.password, user.salt))
				case None => None
			}
		}

		object UserMapper {
			def apply(query: SimpleSql[Row])(implicit connection: Connection) : Option[User] = {
				query
				.singleOpt(int("id") ~ str("email") ~ str("password") ~ str("salt") map(flatten))
				.map(x => User(x._1, x._2, x._3, x._4))
			}
		}
	}
}
