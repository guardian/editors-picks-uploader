organization  := "com.gu"
description   := "AWS Lambda uploading editors picks from Facia to CAPI"
scalacOptions += "-deprecation"
scalaVersion  := "2.11.8"
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-target:jvm-1.8", "-Xfatal-warnings")
name := "editors-picks-uploader"

lazy val editorsPicksUploader = (project in file(".")).enablePlugins(JavaAppPackaging, RiffRaffArtifact)

resolvers += "Guardian GitHub Repository" at "http://guardian.github.io/maven/repo-releases"

val AwsSdkVersion = "1.10.74"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
  "com.amazonaws" % "aws-java-sdk-sts" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-s3" % AwsSdkVersion,
  "com.amazonaws" % "aws-java-sdk-sns" % AwsSdkVersion,
  "com.gu" %% "facia-json-play25" % "2.0.1",
  "com.squareup.okhttp" % "okhttp" % "2.5.0",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test"
)

topLevelDirectory in Universal := None
packageName in Universal := normalizedName.value

riffRaffPackageName := "editors-picks-uploader"
riffRaffPackageType := (packageBin in Universal).value
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")

initialize := {
  val _ = initialize.value
  assert(sys.props("java.specification.version") == "1.8",
    "Java 8 is required for this project.")
}