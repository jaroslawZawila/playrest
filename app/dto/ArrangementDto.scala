package dto

import javax.inject.{Inject, Singleton}

import model.request.{Arrangement => ArrangementRequest}
import model.response.Arrangement
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class ArrangementDto @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val arrangements = TableQuery[ArrangementTable]

  def all(): Future[Seq[Arrangement]] = db.run(arrangements.result)

  def getById(id: Int)(implicit executionContext: ExecutionContext): Future[Option[Arrangement]] =
    db.run(arrangements.filter( _.id === id ).result).map(_.headOption)

  def save(arrangement: ArrangementRequest) = {
    val insertAction = (arrangements returning arrangements.map(_.id)) += Arrangement(0, arrangement.paymentDay, arrangement.status)
    db.run(insertAction.asTry)
  }

  private class ArrangementTable(tag: Tag) extends Table[Arrangement](tag, "arrangements") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def paymentDay = column[Int]("paymentday")
    def status = column[String]("status")

    override def * = (id, paymentDay, status) <> ((Arrangement.apply _).tupled, Arrangement.unapply )
  }
}

