package action

import play.api.mvc.{Action, ActionBuilder, Request, Result}

import scala.concurrent.Future

object AuditAction extends ActionBuilder[Request] {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = block(request)

  override protected def composeAction[A](action: Action[A]): Action[A] = new Audit(action)
}
