package cn.playscala.reactivemongo

import javax.inject.{ Inject, Provider }

import org.mongodb.scala.gridfs.GridFSBucket
import org.mongodb.scala.{ MongoClient, MongoDatabase }
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future

trait ReactiveMongo {

  val name: String

  val databaseName: String

  def mongoClient: MongoClient

  def database: MongoDatabase

  def gridFSBucket: GridFSBucket

  def close(): Unit

}

class DefaultReactiveMongo(config: ReactiveMongoConfig) extends ReactiveMongo {

  override val name: String = config.name

  override val databaseName: String = config.databaseName

  lazy val lazyMongoClient: MongoClient = MongoClient(config.uri)

  lazy val lazyDatabase: MongoDatabase = lazyMongoClient.getDatabase(config.databaseName)

  lazy val lazyGridFSBucket: GridFSBucket = GridFSBucket(lazyDatabase)

  override def mongoClient: MongoClient = lazyMongoClient

  override def database: MongoDatabase = lazyDatabase

  override def gridFSBucket: GridFSBucket = lazyGridFSBucket

  override def close(): Unit = {
    mongoClient.close()
  }

}

class ReactiveMongoProvider(config: ReactiveMongoConfig) extends Provider[ReactiveMongo] {
  @Inject private var applicationLifecycle: ApplicationLifecycle = _
  lazy val get: ReactiveMongo = {
    val defaultReactiveMongo = new DefaultReactiveMongo(config)
    applicationLifecycle.addStopHook(() => Future.successful(defaultReactiveMongo.close()))
    defaultReactiveMongo
  }
}
