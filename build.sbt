name := "reactive-shop"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings

libraryDependencies += "io.sphere" %% "sphere-play-sdk" % "0.51.0" withSources() exclude("org.scala-stm", "scala-stm_2.10.0") exclude("play", "play_2.10")

libraryDependencies += "org.webjars" % "bootstrap" % "3.0.3"

libraryDependencies += "org.webjars" % "bootstrap-glyphicons" % "bdd2cbfba0"

libraryDependencies += "org.webjars" %% "webjars-play" % "2.1.0-3" exclude("play", "play_2.10")

templatesImport += "io.sphere.client.shop.model._"

conflictWarning in ThisBuild ~= { _.copy(level = Level.Debug) }