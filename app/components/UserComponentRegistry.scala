package components

import data.service._
import data.repository._

object UserComponentRegistry 
	extends UserServiceComponent 
	with UserRepositoryComponent
