import interplay.ScalaVersions
import sbt.Keys.organizationName

lazy val commonSettings = Seq(
  scalaVersion := ScalaVersions.scala212,
  crossScalaVersions := Seq(ScalaVersions.scala211, ScalaVersions.scala212)
)

organization := "cn.playscala"
organizationName := "cn.playscala"

// needs to be kept in sync with travis-ci
val PlayVersion = playVersion(sys.env.getOrElse("PLAY_VERSION", "2.6.2"))

lazy val `play-reactive-mongo-root` = (project in file("."))
  .enablePlugins(PlayRootProject, PlayReleaseBase)
  .settings(commonSettings)
  .aggregate(`play-reactive-mongo`)

lazy val `play-reactive-mongo` = (project in file("play-reactive-mongo"))
  .enablePlugins(PlayLibrary)
  .settings(commonSettings)
  .settings(
    organization := "cn.playscala",
    organizationName := "cn.playscala",
    libraryDependencies ++= Seq(
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
      "com.typesafe.play" %% "play" % PlayVersion,
      "com.typesafe.play" %% "play-specs2" % PlayVersion
    )
  )

playBuildRepoName in ThisBuild := "play-reactive-mongo"

homepage := Some(url("https://github.com/playcommunity/play-reactive-mongo"))
scmInfo := Some(ScmInfo(url("https://github.com/playcommunity/play-reactive-mongo"), "git@github.com:playcommunity/play-reactive-mongo.git"))
developers := List(
  Developer(
    "joymufeng",
    "joymufeng",
    "joymufeng@gmail.com",
    url("https://github.com/joymufeng")
  )
)
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

publishMavenStyle := true
publishArtifact in Test := false

// Add sonatype repository settings
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

