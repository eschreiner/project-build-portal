import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "pbp"
    val appVersion      = "0.1-SNAPSHOT"

    val appDependencies = Seq(
      "postgresql" % "postgresql" % "9.1-901.jdbc4",
      "org.squeryl" % "squeryl_2.9.1" % "0.9.5-2",
	  "com.typesafe.akka" % "akka-actor" % "2.0.4",
	  
	  "org.scalatest" %% "scalatest" % "1.8" % "test",
	  "org.specs2" %% "specs2" % "1.12.3" % "test"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
		resolvers += "SpringSource 1" at "http://repository.springsource.com/maven/bundles/release",
		resolvers += "SpringSource 2" at "http://repository.springsource.com/maven/libraries/release",
		resolvers += "SpringSource 3" at "http://repository.springsource.com/maven/bundles/external"
    )

}
