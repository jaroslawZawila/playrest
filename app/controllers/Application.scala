package controllers

import javax.inject.Inject

import dto.ArrangementDto
import model.{Arrangement, ArrangementRequest, CreateResponse}
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.Future

class Application @Inject() (val arrangementDto: ArrangementDto)  extends Controller {

  import model.Arrangements._
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
      case JsSuccess(request: ArrangementRequest, _) => {val x: Future[Result] = arrangementDto.save(request).map{ id => Ok(new CreateResponse(id.toString))}; x}
      case err @ JsError(_) => {val x: Future[Result] = Future(BadRequest(Json.stringify(JsError.toFlatJson(err)))) ; x}
    }).get
  }
}