package controllers

import model.Arrangement
import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.libs.json.{JsValue, Json, OWrites}
import play.api.mvc._

class Application extends Controller {

  def ping = Action {
    Ok("pong")
  }

  def getArrangement(id: String) = Action { implicit request =>

    Ok(new Arrangement(1, id))
  }
}