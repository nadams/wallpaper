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
    "org.joda" % "joda-convert" % "1.2",
    "com.github.nscala-time" %% "nscala-time" % "0.4.2"
  )

  val compassTask = TaskKey[Unit]("compass", "Compile sass")
  val compassTaskSettings = compassTask := {
    Seq("lib/compass.bat", "compile") !
  }

  val main = play.Project(appName, appVersion, appDependencies).settings(
    scalaVersion := "2.10.2",
    compassTaskSettings,
    compile in Compile <<= (compile in Compile).dependsOn(compassTask)
  )
}
