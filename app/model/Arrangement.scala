package model

import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.libs.json.{Json, OWrites, Reads}
import play.api.mvc.Codec

sealed trait JsonResponse

case class Arrangement(id: Int, paymentDay: Int, status: String) extends JsonResponse
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

case class ArrangementRequest(paymentDay: Int, status: String) extends JsonResponse

object ArrangementRequest {
  implicit val readForArrangementRequest: Reads[ArrangementRequest] = Json.reads[ArrangementRequest]
  implicit val arrangementRequestWrites: OWrites[ArrangementRequest] = Json.writes[ArrangementRequest]
}

case class CreateResponse(id: String)

object CreateResponse {

  implicit val createResponseWrites: OWrites[CreateResponse] = Json.writes[CreateResponse]

  implicit def writeableOf_CreateResponse(implicit codec: Codec): Writeable[CreateResponse] = {
    Writeable(jsval => codec.encode(Json.stringify(Json.toJson(jsval))))
  }

  implicit def contentTypeOf_CreateResponse(implicit codec: Codec): ContentTypeOf[CreateResponse] = {
    ContentTypeOf[CreateResponse](Some(ContentTypes.JSON))
  }
}