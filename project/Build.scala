import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
  val appName         = "wallpaper"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,
    anorm,
    "mysql" % "mysql-connector-java" % "5.1.25",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "joda-time" % "joda-time" % "2.1",
    "org.joda" % "joda-convert" % "1.2"
  )

  val compassTask = TaskKey[Unit]("compass", "Compile sass")
  val compassTaskSettings = compassTask := {
    Seq("compass", "compile") !
  }

  val main = play.Project(appName, appVersion, appDependencies).settings(
    scalaVersion := "2.10.2",
    compassTaskSettings,
    compile in Compile <<= (compile in Compile).dependsOn(compassTask)
  )
}
