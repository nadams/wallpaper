package data.service

import java.security._

import data.entities._
import data.repository._

import com.roundeights.hasher.Implicits._

trait UserServiceComponent { this: UserRepositoryComponent =>
	val userService = new UserService

	class UserService {
		def authenticate(username: String, password: String) =
			userRepository.getUserByUsername(username).exists(x => x.password == password.salt(x.salt).bcrypt)

		def createNewUser(email: String, password: String) : Option[User] = {
			val salt = SeedGenerator()
			val hashedPassword = password.salt(salt).bcrypt

			userRepository.insertUser(User(0, email, hashedPassword, salt))
		}
	}
}

object SeedGenerator {
	val generator = SecureRandom.getInstance("SHA1PRNG")

	def apply() : String = {
		val bytes = new Array[Byte](32)

		generator.nextBytes(bytes)

		new String(bytes)
	}
}
