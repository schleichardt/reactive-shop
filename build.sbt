name := "reactive-shop"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings

libraryDependencies += "io.sphere" %% "sphere-play-sdk" % "0.48.0" withSources() exclude("org.scala-stm", "scala-stm_2.10.0") exclude("play", "play_2.10")

templatesImport += "io.sphere.client.shop.model._"