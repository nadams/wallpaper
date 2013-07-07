package data.service

import data.entities._
import data.repository._

import com.roundeights.hasher.Implicits._

trait UserServiceComponent { this: UserRepositoryComponent =>
	val userService = new UserService

	class UserService {
		def authenticate(username: String, password: String) =
			userRepository.getUserByUsername(username).exists(x => x.password == password.salt(x.salt).bcrypt)
	}
}
