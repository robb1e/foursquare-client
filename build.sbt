organization := "com.github.robb1e"

name := "foursquare-client"

version := "0.1.3-SNAPSHOT"

scalaVersion := "2.9.1"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= {
	Seq(
  	  "net.liftweb" %% "lift-json" % "2.4-M4",
  	  "net.databinder" %% "dispatch-http" % "0.8.5",
  	  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
	)
}

resolvers in ThisBuild ++= Seq(
    "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/"
)

crossScalaVersions := Seq("2.9.0", "2.9.1")

publishTo <<= (version) { version: String =>
    val publishType = if (version.endsWith("SNAPSHOT")) "snapshots" else "releases"
    Some(
        Resolver.file(
            "Weave Github " + publishType,
            file(System.getProperty("user.home") + "/dev/robb1e.github.com/maven/repo-" + publishType)
        )
    )
}
