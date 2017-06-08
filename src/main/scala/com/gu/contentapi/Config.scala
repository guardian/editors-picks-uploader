package com.gu.contentapi

import com.amazonaws.regions.{ Region, Regions }

object Config {

  val stage = sys.env.getOrElse("stage", sys.error("Could not find stage variable - Lambda will not run."))

  val nextGenApiUrl = {
    val url = sys.env.getOrElse("nextgenApiUrl", sys.error("Could not find netGenApiUrl variable - Lambda will not run."))
    if (!url.endsWith("/")) s"$url/" else url
  }

  object aws {
    val cmsFrontSTSRoleArn = sys.env.getOrElse("awsCmsFrontSTSRoleArn", sys.error("Could not find awsCmsFrontSTSRoleArn variable - Lambda will not run."))

    val region = {
      val region = sys.env.getOrElse("awsRegion", "eu-west-1")
      Region.getRegion(Regions.fromName(region))
    }

    val topicArn = sys.env.getOrElse("awsTopicArn", sys.error("Could not find awsTopicArn variable - Lambda will not run."))
  }
}
