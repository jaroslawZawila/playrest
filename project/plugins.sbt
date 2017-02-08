logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.9")

resolvers += Resolver.url(
  "bintray-alpeb-sbt-plugins",
  url("https://dl.bintray.com/kamon-io/sbt-plugins/"))(
  Resolver.ivyStylePatterns)

addSbtPlugin("io.kamon" % "sbt-aspectj-play-runner" % "1.0.1")