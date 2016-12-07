package dto

import javax.inject.{Inject, Singleton}

import model.Arrangement
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ArrangementDto @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val arrangements = TableQuery[ArrangementTable]

  def all(): Future[Seq[Arrangement]] = db.run(arrangements.result)

  def getById(id: Int)(implicit executionContext: ExecutionContext): Future[Option[Arrangement]] =
    db.run(arrangements.filter( _.id === id ).result).map(_.headOption)

  private class ArrangementTable(tag: Tag) extends Table[Arrangement](tag, "arrangements") {

    def id = column[Int]("id", O.PrimaryKey)
    def paymentDay = column[Int]("paymentday")
    def status = column[String]("status")

    override def * = (id, paymentDay, status) <> (Arrangement.tupled, Arrangement.unapply )
  }
}

