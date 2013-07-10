package formatters

import org.joda.time.DateTime

import play.api.libs.json._

object DateTimeFormatter {
	val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
	implicit val dateFormat = Format[DateTime](Reads.jodaDateReads(format), Writes.jodaDateWrites(format))
}
