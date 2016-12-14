package controllers

import javax.inject.Inject

import dto.ArrangementDto
import model.{Arrangement, ArrangementRequest, CreateResponse}
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.Future

class Application @Inject() (val arrangementDto: ArrangementDto)  extends Controller {

  import scala.concurrent.ExecutionContext.Implicits.global

  def ping = Action {
    Ok("pong")
  }

  def getArrangement(id: String) = Action async { implicit request =>
    val arrangement: Future[Option[Arrangement]] = arrangementDto.getById(id.toInt)
    arrangement.map {
      case Some(x) => Ok(x)
      case None => NotFound("")
    }
  }

  def saveArrangement = Action async { implicit request =>
    request.body.asJson.map(_.validate[ArrangementRequest] match {
      case JsSuccess(request: ArrangementRequest, _) => arrangementDto.save(request).map{ id => Ok(CreateResponse(id.toString))}
      case err @ JsError(_) => Future(BadRequest(Json.stringify(JsError.toJson(err))))
    }).getOrElse(Future(BadRequest("")))
  }
}