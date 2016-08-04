package com.gu.contentapi.services

import com.amazonaws.auth.{AWSCredentialsProviderChain, InstanceProfileCredentialsProvider}
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client

object S3 {
  lazy val client: AmazonS3Client = new AmazonS3Client()
}
