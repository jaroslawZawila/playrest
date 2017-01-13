package action

import play.api.Logger
import play.api.mvc._

import scala.concurrent.Future

case class Audit[A](action: Action[A]) extends Action[A] {

  override def apply(request: Request[A]): Future[Result] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    request.headers.get("User") match {
      case Some(user) => {
        Logger.info(s"Audit message here: ${user}")
        action(request)
      }
      case _ => Future(Results.BadRequest("Missing Audit header."))
    }
  }

  lazy val parser: BodyParser[A] = action.parser
}
