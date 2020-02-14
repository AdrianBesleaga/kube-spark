import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.uprush"
ThisBuild / organizationName := "uprush"

lazy val root = (project in file("."))
  .settings(
    name := "doglover",
    description := "Spark example to analyze dog lover's tweets",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.apache.spark" %% "spark-sql" % "2.4.5"
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
