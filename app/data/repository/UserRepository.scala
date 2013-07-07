package data.repository

import data.entities._

trait UserRepositoryComponent {
	val userRepository = new UserRepository
	
	class UserRepository {
		val user = User(1, "test", "password", "")

		def getUserById(id: Int) : Option[User] = Option(user)
		def getUserByUsername(username: String) : Option[User] = Option(user)
	}
}
