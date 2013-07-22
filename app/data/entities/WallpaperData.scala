package data.entities

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import data.mongoContext._

case class WallpaperData(
	wallpaperId: Int,
	data: Array[Byte]
)

object WallpaperData extends ModelCompanion[WallpaperData, Int] {
	val dao = new SalatDAO[WallpaperData, Int](collection = mongoCollection("wallpaper")) {}

	def getByWallpaperId(id: Int) : Option[WallpaperData] = dao.findOne(MongoDBObject("id" -> id))
}
