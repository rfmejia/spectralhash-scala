name := """spectralhash-scala"""

organization := "com.github.rfmejia"

version := "0.1"

scalaVersion := "2.11.6"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

scalacOptions ++= Seq(
  "-Xlint",
  "-deprecation",
  "-Xfatal-warnings",
  "-feature",
  "-unchecked",
  "-encoding", "utf8")

scalariformSettings
