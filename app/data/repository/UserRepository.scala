package data.repository

import org.joda.time.{ DateTime, DateTimeZone }
import org.joda.time.format.DateTimeFormat
import java.sql.Timestamp

import anorm._ 
import anorm.SqlParser._
import java.sql._
import play.api.db.DB
import play.api.Play.current
import AnormExtension._

import data.entities._

trait UserRepositoryComponent {
	val userRepository = new UserRepository
	
	class UserRepository {
		val table = "user"
		val columns = "id, email, password, dateCreated"
		val pattern = "yyyy-MM-dd HH:mm:ss"
		val formatter = DateTimeFormat.forPattern(pattern)

		def getUserById(id: Int) : Option[User] = DB.withConnection { implicit connection => 
			val query = SQL(
				s"""
					SELECT $columns
					FROM $table AS u
					WHERE u.id = {id} ;
				"""
			).on("id" -> id)

			mapUser(query)
		}

		def getUserByUsername(email: String) : Option[User] = DB.withConnection { implicit connection => 
			val query = SQL(
				s"""
					SELECT $columns
					FROM $table AS u
					WHERE u.email = {email} ;
				"""
			).on("email" -> email)

			mapUser(query)
		}

		def insertUser(user: User) : Option[User] = DB.withConnection { implicit connection => 
			SQL(
				s"""
					INSERT INTO $table(`email`, `password`, `dateCreated`)
					VALUES({email}, {password}, {dateCreated}) ;
				"""
			).on(
				"email" -> user.email,
				"password" -> user.password,
				"dateCreated" -> user.dateCreated.toString(pattern)
			).executeInsert()
		} match {
			case Some(long) => Some(User(long, user.email, user.password, user.dateCreated))
			case None => None
		}
		
		def mapUser(query: SimpleSql[Row])(implicit connection: Connection) : Option[User] = query
			.singleOpt(long("id") ~ str("email") ~ str("password") ~ get[DateTime]("dateCreated") map(flatten))
			.map (x => User(x._1, x._2, x._3, x._4))
	}
}
