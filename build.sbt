organization  := "com.gu"
description   := "AWS Lambda uploading editors picks from Facia to CAPI"
scalacOptions += "-deprecation"
scalaVersion  := "2.13.10"
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-release", "8", "-Xfatal-warnings")
name := "editors-picks-uploader"

lazy val editorsPicksUploader = (project in file(".")).enablePlugins(JavaAppPackaging, RiffRaffArtifact)

val AwsSdkVersion = "1.12.261"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.2",
  "com.amazonaws" % "aws-java-sdk-sts" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-s3" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-sns" % AwsSdkVersion,
  "com.gu" %% "facia-json-play27" % "4.0.5",
  "com.typesafe.play" %% "play-json-joda" % "2.9.4",
  "com.squareup.okhttp" % "okhttp" % "2.7.5",
  "org.scalatest" %% "scalatest" % "3.2.15" % "test"
)

dependencyOverrides ++=  Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.14.2",
  "com.fasterxml.jackson.dataformat"  % "jackson-dataformat-cbor" % "2.14.2"
)

Universal / topLevelDirectory := None
Universal / packageName  := normalizedName.value

riffRaffManifestProjectName := s"Content Platforms::editors-picks-uploader-lambda"
riffRaffPackageName := "editors-picks-uploader"
riffRaffPackageType := (Universal / packageBin).value
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")

initialize := {
  val _ = initialize.value
  assert(sys.props("java.specification.version") == "1.8",
    "Java 8 is required for this project.")
}