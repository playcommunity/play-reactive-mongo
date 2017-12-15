package cn.playscala.reactivemongo

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.WithApplication

class ReactiveMongoGuiceSpec extends Specification with Mockito {

  val application = new GuiceApplicationBuilder()
    .configure(
      "mongodb.uri" -> "mongodb://qizhi:qizhi&123@106.75.6.179:52174/cloud-vip-test?authMode=scram-sha1",
      "mongodb.abc.uri" -> "mongodb://qizhi:qizhi&123@106.75.6.179:52174/cloud-vip-abc?authMode=scram-sha1"
    )
    .bindings(new ReactiveMongoModule)
    .build()

  "The ReactiveMongoModule" should {
    "should provide ReactiveMongo" in new WithApplication(application) {
      val reactiveMongo = app.injector.instanceOf(classOf[ReactiveMongo])
      reactiveMongo must beAnInstanceOf[ReactiveMongo]
    }
  }

}
