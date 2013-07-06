package controllers

import play.api._
import play.api.mvc._
import models.MenuModel

trait ProvidesHeader {
	implicit def header[A](implicit request: Request[A]) : MenuModel = {
		val user = request.session.get("username")

		MenuModel(user)
	}
}