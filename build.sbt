
name := "LeaderBoard"

version := "0.1"

scalaVersion := "2.11.12"

idePackagePrefix := Some("org.leaderboard.jobs")

val sparkVersion = "2.4.6"
val jedisVersion = "3.1.0"
val sparkRedisVersion = "2.4.0"
val mysqlVersion = "8.0.22"
val awsSdkVersion = "1.7.4"
val hdpVersion = "2.7.3"
val cpoolVersion = "2.9.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "redis.clients" % "jedis" % jedisVersion % "provided",
  "com.redislabs" % "spark-redis" % sparkRedisVersion % "provided",
  "org.apache.commons" %  "commons-pool2" % cpoolVersion % "provided",
  "mysql" % "mysql-connector-java" % mysqlVersion % "provided",
  "com.amazonaws" % "aws-java-sdk" % awsSdkVersion % "provided",
  "org.apache.hadoop" % "hadoop-aws" % hdpVersion % "provided"
)


assemblyMergeStrategy in assembly := {
  case PathList("org", "aopalliance", xs@_*) => MergeStrategy.last
  case PathList("javax", "inject", xs@_*) => MergeStrategy.last
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.last
  case PathList("javax", "activation", xs@_*) => MergeStrategy.last
  case PathList("org", "apache", xs@_*) => MergeStrategy.last
  case PathList("com", "google", xs@_*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs@_*) => MergeStrategy.last
  case PathList("com", "codahale", xs@_*) => MergeStrategy.last
  case PathList("com", "yammer", xs@_*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}