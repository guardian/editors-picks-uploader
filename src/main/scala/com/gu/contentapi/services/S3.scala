package com.gu.contentapi.services

import com.amazonaws.auth.AWSCredentialsProviderChain
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client

object S3 {

  private val credentials = new AWSCredentialsProviderChain(
    new ProfileCredentialsProvider("capi"),
    new ProfileCredentialsProvider()
  )

  lazy val client: AmazonS3Client = new AmazonS3Client(credentials)
}
