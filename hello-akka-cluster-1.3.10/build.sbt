organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

// lagomCassandraEnabled in ThisBuild := true
// lagomServiceLocatorEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false

lazy val `hello-akka-cluster` = (project in file("."))
  .aggregate(`hello-akka-cluster-api`, `hello-akka-cluster-impl`)

lazy val `hello-akka-cluster-api` = (project in file("hello-akka-cluster-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `hello-akka-cluster-impl` = (project in file("hello-akka-cluster-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`hello-akka-cluster-api`)
