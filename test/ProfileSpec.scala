package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import models.profile._

class ProfileSpec extends Specification {
	"registerForm" should {
		"require valid email address" in {
			val registerForm = ProfileForms.registerForm
			val registerModel = RegisterModel("invalidEmailAddress", "", "")
			val result = registerForm.fillAndValidate(registerModel)

			result("email").error.get.key must equalTo("user.email")
		}
	}
}