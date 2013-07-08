package data.repository

import java.nio.charset.Charset

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

		val user = User(1, "test", "password")

		def getUserById(id: Int) : Option[User] = {
			DB.withConnection { implicit connection => 
				val query = SQL(
					s"""
						SELECT id, email, password, dateCreated
						FROM $table AS u
						WHERE u.id = {id} ;
					"""
				).on("id" -> id)

				UserMapper(query)
			}
		}

		def getUserByUsername(email: String) : Option[User] = {
			DB.withConnection { implicit connection => 
				val query = SQL(
					s"""
						SELECT id, email, password, dateCreated
						FROM $table AS u
						WHERE u.email = {email} ;
					"""
				).on("email" -> email)

				UserMapper(query)
			}
		}

		def insertUser(user: User) : Option[User] = {
			DB.withConnection { implicit connection => 
				SQL(
					s"""
						INSERT INTO $table(`email`, `password`, `dateCreated`)
						VALUES({email}, {password}, NOW()) ;
					"""
				).on(
					"email" -> user.email,
					"password" -> user.password
				).executeInsert()
			} match {
				case Some(long) => Some(User(long, user.email, user.password))
				case None => None
			}
		}

		object UserMapper {
			val charset = Charset.forName("US-ASCII")

			def apply(query: SimpleSql[Row])(implicit connection: Connection) : Option[User] = {
				query
				.singleOpt(long("id") ~ str("email") ~ str("password") map(flatten))
				.map(x => User(x._1, x._2, x._3))
			}
		}
	}
}
