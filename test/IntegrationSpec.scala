import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import org.specs2.matcher.Matchers
import play.api.libs.ws.WSResponse
import play.api.test._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification with Matchers {

  import scala.concurrent.ExecutionContext.Implicits.global

  "Application" should {

    "ping resource respond with pong" in new WithServer() {

      val responseFuture = WsTestClient.wsCall(controllers.routes.Application.ping).get()
      val response = Await.result(responseFuture, 1.second)

      response.body shouldEqual("pong")
    }

    "get arrangement resource response is correct" in new WithServer() {
      val responseFuture = WsTestClient.wsCall(controllers.routes.Application.getArrangement("1")).get()
      val response = Await.result(responseFuture, 1.second)

      response.body shouldEqual("{\"paymentDay\":1,\"status\":\"2\"}")
    }
  }
}
