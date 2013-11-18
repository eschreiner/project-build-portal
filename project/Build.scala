import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "pbp"
    val appVersion      = "0.1-SNAPSHOT"

    val appDependencies = Seq(
		jdbc,
//		cache,
      "postgresql" % "postgresql" % "9.1-901.jdbc4",
      "org.squeryl" %% "squeryl" % "0.9.5-6",
	  
	  "org.scalatest" %% "scalatest" % "1.9.2" % "test",
	  "org.specs2" %% "specs2" % "2.3.4" % "test"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
		resolvers += "SpringSource 1" at "http://repository.springsource.com/maven/bundles/release",
		resolvers += "SpringSource 2" at "http://repository.springsource.com/maven/libraries/release",
		resolvers += "SpringSource 3" at "http://repository.springsource.com/maven/bundles/external"
    )

}
