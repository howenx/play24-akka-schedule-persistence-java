import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayJava

name := "play24-akka-schedule-persistence-java"
version := "0.1.5"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

evictionWarningOptions in evicted := EvictionWarningOptions.default.withWarnScalaVersionEviction(false)

updateOptions := updateOptions.value.withConsolidatedResolution(true)


libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)


libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "com.squareup.okhttp" % "okhttp" % "2.7.2"
libraryDependencies += "commons-beanutils" % "commons-beanutils" % "1.9.2"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4" withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.1" withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" % "akka-kernel_2.11" % "2.4.1" withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" % "akka-slf4j_2.11" % "2.4.1" withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.4.1" withSources() withJavadoc()

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence" % "2.4.1",
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
)

javacOptions += "-Xlint:deprecation"

routesGenerator := InjectedRoutesGenerator
