# play-reactive-mongo
A module of play scala 2.6.x for [official mongo-scala-driver](https://github.com/mongodb/mongo-scala-driver).

# How to use ?
Add to project:
```
libraryDependencies += "cn.playscala" % "play-reactive-mongo_2.12" % "0.1.0"
```
Use in code:
```
class HomeController @Inject()(cc: ControllerComponents, reactiveMongo: ReactiveMongo) extends AbstractController(cc) {
  val databaseWithCodec = reactiveMongo.database.withCodecRegistry(Codec.registry)
  val qaCol: MongoCollection[QA] = databaseWithCodec.getCollection("common-qa")
}
```
