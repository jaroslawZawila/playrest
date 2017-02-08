package controllers

import javax.inject.Inject

import action.AuditAction
import dto.ArrangementDto
import kamon.Kamon
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.Future
import scala.util.Success
import model.response.{Arrangement, Create}
import model.request.{Arrangement => ArrangementRequest}

class Application @Inject() (val arrangementDto: ArrangementDto)  extends Controller {

  Kamon.start()

  import scala.concurrent.ExecutionContext.Implicits.global

  def ping = Action {
    Ok("pong")
  }

  def getArrangement(id: String) = AuditAction async { implicit request =>
    val arrangement: Future[Option[Arrangement]] = arrangementDto.getById(id.toInt)
    arrangement.map {
      case Some(x) => Ok(x)
      case None => NotFound("")
    }
  }

  def saveArrangement = Action async { implicit request =>
    request.body.asJson.map(_.validate[ArrangementRequest] match {
      case JsSuccess(request: ArrangementRequest, _) => arrangementDto.save(request).map{
        case Success(id) => Ok(Create(id.toString))
        case _ => BadRequest("{\"id\":\"ERROR\"}")}
      case err @ JsError(_) => Future(BadRequest(Json.stringify(JsError.toJson(err))))
    }).getOrElse(Future(BadRequest("")))
  }
}