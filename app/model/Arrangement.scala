package model

import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.mvc.Codec

sealed trait JsonResponse

case class Arrangement(paymentDay: Int, status: String) extends JsonResponse

object Arrangement {

  import play.api.libs.json._

  implicit val arrangementWrites: OWrites[Arrangement] = Json.writes[Arrangement]

  implicit def writeableOf_ArgonautJson(implicit codec: Codec): Writeable[Arrangement] = {
    Writeable(jsval => codec.encode(Json.stringify(Json.toJson(jsval))))
  }

  implicit def contentTypeOf_ArgonautJson(implicit codec: Codec): ContentTypeOf[Arrangement] = {
    ContentTypeOf[Arrangement](Some(ContentTypes.JSON))
  }
}
