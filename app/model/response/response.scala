package model.response

import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.Codec


case class Arrangement(id: Int, paymentDay: Int, status: String)
object Arrangement {

  implicit val arrangementWrites: OWrites[Arrangement] = Json.writes[Arrangement]

  implicit def writeableOf_ArgonautJson(implicit codec: Codec): Writeable[Arrangement] = {
    Writeable(jsval => codec.encode(Json.stringify(Json.toJson(jsval))))
  }

  implicit def contentTypeOf_ArgonautJson(implicit codec: Codec): ContentTypeOf[Arrangement] = {
    ContentTypeOf[Arrangement](Some(ContentTypes.JSON))
  }
  implicit val readForArrangement: Reads[Arrangement] = Json.reads[Arrangement]

}


case class Create(id: String)

object Create {

  implicit val createResponseWrites: OWrites[Create] = Json.writes[Create]

  implicit def writeableOf_CreateResponse(implicit codec: Codec): Writeable[Create] = {
    Writeable(jsval => codec.encode(Json.stringify(Json.toJson(jsval))))
  }

  implicit def contentTypeOf_CreateResponse(implicit codec: Codec): ContentTypeOf[Create] = {
    ContentTypeOf[Create](Some(ContentTypes.JSON))
  }
}

