package model.request

import play.api.libs.json.{Json, OWrites, Reads}

case class Arrangement(paymentDay: Int, status: String)

object Arrangement {
  implicit val readForArrangementRequest: Reads[Arrangement] = Json.reads[Arrangement]
  implicit val arrangementRequestWrites: OWrites[Arrangement] = Json.writes[Arrangement]
}

