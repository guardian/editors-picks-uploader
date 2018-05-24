organization  := "com.gu"
description   := "AWS Lambda uploading editors picks from Facia to CAPI"
scalacOptions += "-deprecation"
scalaVersion  := "2.12.6"
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-target:jvm-1.8", "-Xfatal-warnings")
name := "editors-picks-uploader"

lazy val editorsPicksUploader = (project in file(".")).enablePlugins(JavaAppPackaging, RiffRaffArtifact)

val AwsSdkVersion = "1.11.335"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
  "com.amazonaws" % "aws-java-sdk-sts" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-s3" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-sns" % AwsSdkVersion,
  "com.gu" %% "facia-json-play26" % "2.6.0",
  "com.typesafe.play" %% "play-json-joda" % "2.6.9",
  "com.squareup.okhttp" % "okhttp" % "2.5.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

topLevelDirectory in Universal := None
packageName in Universal := normalizedName.value

riffRaffManifestProjectName := s"Content Platforms::editors-picks-uploader-lambda"
riffRaffPackageName := "editors-picks-uploader"
riffRaffPackageType := (packageBin in Universal).value
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")

initialize := {
  val _ = initialize.value
  assert(sys.props("java.specification.version") == "1.8",
    "Java 8 is required for this project.")
}