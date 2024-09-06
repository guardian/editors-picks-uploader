organization  := "com.gu"
description   := "AWS Lambda uploading editors picks from Facia to CAPI"
scalacOptions += "-deprecation"
scalaVersion  := "2.13.14"
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xfatal-warnings")
name := "editors-picks-uploader"

lazy val editorsPicksUploader = (project in file(".")).enablePlugins(JavaAppPackaging)

val AwsSdkVersion = "1.12.765"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.2",
  "com.amazonaws" % "aws-java-sdk-sts" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-s3" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-sns" % AwsSdkVersion,
  "com.gu" %% "facia-json-play30" % "9.0.0",
  "com.typesafe.play" %% "play-json-joda" % "2.10.6",
  "com.squareup.okhttp" % "okhttp" % "2.7.5",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test"
)

dependencyOverrides ++=  Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.15.4",
  "com.fasterxml.jackson.dataformat"  % "jackson-dataformat-cbor" % "2.15.4"
)

Universal / topLevelDirectory := None
Universal / packageName  := normalizedName.value

Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-u", sys.env.getOrElse("SBT_JUNIT_OUTPUT", "junit"), "-o")
