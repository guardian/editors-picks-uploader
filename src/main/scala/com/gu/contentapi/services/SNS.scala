package com.gu.contentapi.services

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.{AWSCredentialsProviderChain, InstanceProfileCredentialsProvider}
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.sns.AmazonSNSClient
import com.amazonaws.services.sns.model.{PublishRequest, PublishResult}
import com.gu.contentapi.Config

import scala.util.Try

class SNS(val config: Config) {

  private lazy val snsClient: AmazonSNSClient = {

    val snsClientConfiguration = new ClientConfiguration().withConnectionTimeout(20000).withSocketTimeout(20000)
    val snsClient = new AmazonSNSClient(snsClientConfiguration)
    snsClient.setRegion(config.aws.region)
    snsClient
  }

  def publish(topicArn: String, message: String): Try[PublishResult] = {
    val request = new PublishRequest(topicArn, message)

    Try(snsClient.publish(request))
  }

}
