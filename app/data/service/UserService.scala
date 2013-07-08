package data.service

import org.mindrot.jbcrypt.BCrypt

import data.entities._
import data.repository._

trait UserServiceComponent { this: UserRepositoryComponent =>
	val userService = new UserService

	class UserService {
		def authenticate(username: String, password: String) =
			userRepository.getUserByUsername(username).exists(x => BCrypt.checkpw(password, x.password))

		def createNewUser(email: String, password: String) : Option[User] = {
			val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

			userRepository.insertUser(User(0, email, hashedPassword))
		}

		def getUserByUsername(username: String) : Option[User] =
			userRepository.getUserByUsername(username)
	}
}
