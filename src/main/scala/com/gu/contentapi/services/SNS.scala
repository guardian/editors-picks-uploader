package com.gu.contentapi.services

import com.amazonaws.ClientConfiguration
import com.amazonaws.services.sns.{ AmazonSNS, AmazonSNSClientBuilder }
import com.amazonaws.services.sns.model.{ PublishRequest, PublishResult }
import com.gu.contentapi.Config

import scala.util.Try

object SNS {

  private val snsClient: AmazonSNS = {

    val snsClientConfiguration = new ClientConfiguration().withConnectionTimeout(20000).withSocketTimeout(20000)

    AmazonSNSClientBuilder.standard
      .withClientConfiguration(snsClientConfiguration)
      .withRegion(Config.aws.region.getName)
      .build
  }

  def publish(topicArn: String, message: String): Try[PublishResult] = {
    val request = new PublishRequest(topicArn, message)

    Try(snsClient.publish(request))
  }

}
