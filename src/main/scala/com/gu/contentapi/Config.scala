package com.gu.contentapi

import java.util.Properties
import com.amazonaws.regions.{ Region, Regions }
import com.gu.contentapi.services.S3

import scala.util.Try

class Config(functionName: String) {

  val isProd = Try(functionName.toLowerCase.contains("-prod")).getOrElse(false)
  val stage = if (isProd) "PROD" else "CODE"
  private val config = loadConfig()

  val nextGenApiUrl = {
    val url = Option(config.getProperty("nextgenApiUrl")) getOrElse sys.error("Could not load netGenApiUrl from config - Lambda will not run.")
    if (!url.endsWith("/")) s"$url/" else url
  }

  object aws {
    val cmsFrontSTSRoleArn = Option(config.getProperty("aws.cmsFrontSTSRoleArn")) getOrElse sys.error("Could not load aws.cmsFrontSTSRoleArn from config - Lambda will not run.")

    val region = {
      val region = Option(config.getProperty("aws.region")) getOrElse ("eu-west-1")
      Region.getRegion(Regions.fromName(region))
    }

    val topicArn = Option(config.getProperty("aws.topicArn")) getOrElse sys.error("Could not load aws.topicArn from config - Lambda will not run.")
  }

  private def loadConfig() = {
    val configFileKey = s"editors-picks-uploader/$stage/config.properties"
    val configInputStream = S3.client.getObject("content-api-config", configFileKey).getObjectContent
    val configFile: Properties = new Properties()
    Try(configFile.load(configInputStream)) orElse sys.error("Could not load config file from s3. This lambda will not run.")
    configFile
  }
}
