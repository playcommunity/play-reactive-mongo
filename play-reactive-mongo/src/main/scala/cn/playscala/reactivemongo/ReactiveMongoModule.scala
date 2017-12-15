package cn.playscala.reactivemongo

import javax.inject.{ Inject, Singleton }
import play.api.{ Configuration, Environment }
import play.api.inject.Module

class ReactiveMongoModule extends Module {
  def bindings(environment: Environment, configuration: Configuration) = {
    val configSeq = ReactiveMongoConfig.parse(configuration)
    configSeq.map{ config =>
      val reactiveMongoProvider = new ReactiveMongoProvider(config)
      if (config.name == "default") {
        bind[ReactiveMongo].to(reactiveMongoProvider)
      } else {
        bind[ReactiveMongo].qualifiedWith(config.name).to(reactiveMongoProvider)
      }
    }
  }

}
