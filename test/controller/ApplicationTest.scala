package controller

import akka.stream.Materializer
import model.request.{Arrangement => ArrangementRequest}
import model.response.Arrangement
import org.junit.runner._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.junit.JUnitRunner
import org.scalatestplus.play._
import play.api.db.DBApi
import play.api.db.evolutions.{Evolution, Evolutions, SimpleEvolutionsReader}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json._
import play.api.mvc.Result
import play.api.test.Helpers.{GET, _}
import play.api.test._
import play.api.{Application, Configuration, Mode}

import scala.concurrent.Future

@RunWith(classOf[JUnitRunner])
class ApplicationTest extends PlaySpec with OneAppPerSuite with BeforeAndAfterAll {

    implicit override lazy val app: Application = new GuiceApplicationBuilder().configure(
    Configuration.from(
      Map(
        "slick.dbs.default.driver" -> "slick.driver.H2Driver$",
        "slick.dbs.default.db.driver" -> "org.h2.Driver",
        "slick.dbs.default.db.url" -> "jdbc:h2:mem:default;TRACE_LEVEL_SYSTEM_OUT=1;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_UPPER=FALSE",
        "play.evolutions.db.default.autoApplyDowns" -> "true",
        "play.evolutions.db.default.autoApply" -> "true",
        "play.evolutions.db.db.autoApply" -> "true"
      )))
    .in(Mode.Test)
    .build()

  implicit lazy val materializer: Materializer = app.materializer

  "Application action" should {
    "ping returns pong" in {

      val request = FakeRequest(GET, "/ping")

      val result: Future[Result] = route(app, request).get

      status(result) mustEqual OK
      contentAsString(result) mustEqual "pong"
    }

    "arrangement returns correct arrangement" in {

      val request = FakeRequest(GET, "/arrangement/1")

      val result: Future[Result] = route(app, request).get

      status(result) mustEqual OK

      val arrangement = contentAsJson(result).as[Arrangement]
      arrangement.id mustEqual 1
      arrangement.paymentDay mustEqual 15
      arrangement.status mustEqual "ACTIVE"
    }

    "arrangement returns not found if arrangement does not exist" in {

      val request = FakeRequest(GET, "/arrangement/999")

      val result: Future[Result] = route(app, request).get

      status(result) mustEqual NOT_FOUND
    }

    "save arrangement returns id" in {

      val body = ArrangementRequest(12, "ACTIVE")
      val request = FakeRequest(POST, "/arrangement/").withJsonBody(Json.toJson(body))

      val result: Future[Result] = route(app, request).get

      status(result) mustEqual OK
      (contentAsJson(result) \ "id" ).get.as[String] must be("2")
    }

    "save invalid arrangement returns error" in {

      val request = FakeRequest(POST, "/arrangement/").withBody("""{}""")

      val result: Future[Result] = route(app, request).get

      status(result) mustEqual BAD_REQUEST
    }

    "save arrangement returns error for sure" in {

      val body = ArrangementRequest(12, "ACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVEACTIVE")
      val request = FakeRequest(POST, "/arrangement/").withJsonBody(Json.toJson(body))

      val result: Future[Result] = route(app, request).get

      status(result) mustEqual BAD_REQUEST
    }

  }

  override def beforeAll(): Unit = {
  Evolutions.applyEvolutions(app.injector.instanceOf[DBApi].database("default"), SimpleEvolutionsReader.forDefault(
    Evolution(
      999,
      "INSERT INTO arrangements (id, paymentday, status) VALUES (1, 15, 'ACTIVE');",
      "DELETE FROM arrangements WHERE id=1;"
    )
  ))
}

}
