import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import org.specs2.matcher.Matchers
import play.api.test._
import play.api.db.{Databases, Database}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification with Matchers with BeforeAfter {


  def before = {

  }

  override def after: Any = {}

  def withMyDatabase[T](block: Database => T) = {
    Databases.withInMemory(
      name = "postgres",
      urlOptions = Map(
        "MODE" -> "POSTGRES"
      ),
      config = Map(
        "logStatements" -> true
      )
    )(block)
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  "Application" should {

    "ping resource respond with pong" in new WithServer() {

      val responseFuture = WsTestClient.wsCall(controllers.routes.Application.ping).get()
      val response = Await.result(responseFuture, 1.second)

      response.body shouldEqual("pong")
    }

    "get arrangement resource response is correct" in new WithServer() {
      val responseFuture = WsTestClient.wsCall(controllers.routes.Application.getArrangement("2")).get()
      val response = Await.result(responseFuture, 5.second)

      response.status shouldEqual(400)
    }
  }


}
