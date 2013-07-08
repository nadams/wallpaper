package data.repository

import java.nio.charset.Charset
import org.joda.time.{ DateTime, DateTimeZone }

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
		val columns = "id, email, password, dateCreated"

		val user = User(1, "test", "password")

		def getUserById(id: Int) : Option[User] = {
			DB.withConnection { implicit connection => 
				val query = SQL(
					s"""
						SELECT $columns
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
						SELECT $columns
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
						VALUES({email}, {password}, {dateCreated}) ;
					"""
				).on(
					"email" -> user.email,
					"password" -> user.password,
					"dateCreated" -> user.dateCreated
				).executeInsert()
			} match {
				case Some(long) => Some(User(long, user.email, user.password, user.dateCreated))
				case None => None
			}
		}

		object UserMapper {
			val charset = Charset.forName("US-ASCII")

			def apply(query: SimpleSql[Row])(implicit connection: Connection) : Option[User] = {
				query
				.singleOpt(long("id") ~ str("email") ~ str("password") ~ date("dateCreated") map(flatten))
				.map(x => User(x._1, x._2, x._3, new DateTime(x._4, DateTimeZone.UTC)))
			}
		}
	}
}
