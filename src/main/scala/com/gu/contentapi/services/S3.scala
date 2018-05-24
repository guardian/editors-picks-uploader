package com.gu.contentapi.services

import com.amazonaws.services.s3.{ AmazonS3, AmazonS3ClientBuilder }

object S3 {
  val client: AmazonS3 = AmazonS3ClientBuilder.defaultClient
}
