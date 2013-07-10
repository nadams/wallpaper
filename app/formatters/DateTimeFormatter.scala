package formatters

import org.joda.time.DateTime

import play.api.libs.json._

object DateTimeFormatter {
	implicit val dateFormat = { 
		val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
		Format[DateTime](Reads.jodaDateReads(format), Writes.jodaDateWrites(format))
	}
}
