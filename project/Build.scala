import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "wallpaper"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )

  val compassTask = TaskKey[Unit]("compass", "Compile sass")
  val compassTaskSettings = compassTask := {
    Seq("compass", "compile") !
  }

  val main = play.Project(appName, appVersion, appDependencies).settings(
    compassTaskSettings,
    compile in Compile <<= (compile in Compile).dependsOn(compassTask)
  )

}
