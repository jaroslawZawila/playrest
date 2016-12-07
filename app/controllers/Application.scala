package controllers

import javax.inject.Inject

import dto.ArrangementDto
import model.Arrangement
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
}