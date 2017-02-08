name := "playrest"

version := "1.0"

lazy val `playrest` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( cache , ws   , specs2 % Test, evolutions,
  "com.typesafe.play" %% "play-slick"             % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions"  % "2.0.0",
  "org.postgresql"    % "postgresql"              % "9.4.1212",
  "com.h2database"    % "h2"                      % "1.4.190" ,
  "org.scalatestplus.play" % "scalatestplus-play_2.11" % "1.5.1",
//  Metrics dependencies
  "io.kamon" %% "kamon-core" % "0.6.0",
  "io.kamon" %% "kamon-play-25" % "0.6.2",
  "io.kamon" %% "kamon-log-reporter" % "0.6.0",
  "com.monsanto.arch" %% "kamon-prometheus" % "0.2.0",
  "io.prometheus" % "simpleclient_common" % "0.0.20"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

testOptions in Test := Seq(Tests.Filter(s => s.endsWith("Test")))

fork in run := false



