import com.typesafe.sbt.SbtScalariform._
import com.typesafe.tools.mima.plugin.MimaKeys.previousArtifacts
import com.typesafe.tools.mima.plugin.MimaPlugin.mimaDefaultSettings
import interplay.ScalaVersions
import scalariform.formatter.preferences._

lazy val commonSettings = SbtScalariform.scalariformSettings ++ Seq(
  scalaVersion := ScalaVersions.scala212,
  crossScalaVersions := Seq(ScalaVersions.scala211, ScalaVersions.scala212),
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(SpacesAroundMultiImports, true)
    .setPreference(SpaceInsideParentheses, false)
    .setPreference(DanglingCloseParenthesis, Preserve)
    .setPreference(PreserveSpaceBeforeArguments, true)
    .setPreference(DoubleIndentClassDeclaration, true)
)

// needs to be kept in sync with travis-ci
val PlayVersion = playVersion(sys.env.getOrElse("PLAY_VERSION", "2.6.2"))

lazy val `play-reactive-mongo` = (project in file("play-reactive-mongo"))
  .enablePlugins(PlayLibrary)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
      "com.typesafe.play" %% "play" % PlayVersion,
      "com.typesafe.play" %% "play-specs2" % PlayVersion
    )
  )

lazy val `play-reactive-mongo-root` = (project in file("."))
  .enablePlugins(PlayRootProject, PlayReleaseBase)
  .settings(commonSettings)
  .aggregate(`play-reactive-mongo`)

playBuildRepoName in ThisBuild := "play-reactive-mongo"

mimaDefaultSettings

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

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

