package models

import play.api.data.Field
import play.api.i18n.{ Messages, Lang }

object FieldExtensions {
	class FieldError(field: Field)(implicit lang: Lang) {
		def formattedMessage : Tuple2[String, Option[String]] =
			(field.value.getOrElse(""), field.error.map { error => Messages(error.message, error.args: _*) })
	}

	implicit def formattedMessage(field: Field)(implicit lang: Lang) = 
		new FieldError(field)
}
