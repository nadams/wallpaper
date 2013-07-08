package data.service

import data.entities._
import data.repository._

import com.roundeights.hasher.Implicits._

trait UserServiceComponent { this: UserRepositoryComponent =>
	val userService = new UserService

	class UserService {
		def authenticate(username: String, password: String) =
			userRepository.getUserByUsername(username).exists(x => password bcrypt= x.password)

		def createNewUser(email: String, password: String) : Option[User] = 
			userRepository.insertUser(User(0, email, password.bcrypt))
	}
}
