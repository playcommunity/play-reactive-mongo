package cn.playscala.reactivemongo

import javax.inject.Inject

import com.mongodb.ConnectionString
import play.api.Configuration

case class ReactiveMongoConfig(name: String, databaseName: String, uri: String)

object ReactiveMongoConfig {
  def parse(config: Configuration): Seq[ReactiveMongoConfig] = {
    val mongoConfig = config.get[Configuration]("mongodb")
    mongoConfig.keys.filter(_.endsWith("uri")).map { key =>
      val uri = mongoConfig.get[String](key)
      val dbName = new ConnectionString(uri).getDatabase
      if (key == "uri") {
        ReactiveMongoConfig("default", dbName, uri)
      } else {
        ReactiveMongoConfig(key.split("[.]")(0), dbName, uri)
      }
    }.toSeq
  }
}

