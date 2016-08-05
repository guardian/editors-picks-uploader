package com.gu.contentapi.services

import com.amazonaws.auth.{ AWSCredentialsProviderChain, STSAssumeRoleSessionCredentialsProvider }
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.gu.contentapi.Config
import com.gu.facia.client.{ AmazonSdkS3Client, ApiClient }
import com.squareup.okhttp.Response
import play.api.libs.json.{ JsArray, Json }

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

class Facia(config: Config) {

  private val FaciaBucket = "facia-tool-store"

  private val client: ApiClient = {

    val cmsFrontsCredentials = new AWSCredentialsProviderChain(
      new ProfileCredentialsProvider("cmsFronts"),
      new STSAssumeRoleSessionCredentialsProvider(config.aws.cmsFrontSTSRoleArn, "frontend")
    )

    val client = new AmazonS3Client(cmsFrontsCredentials)
    client.setEndpoint(config.aws.region.getServiceEndpoint("s3"))
    ApiClient(FaciaBucket, config.stage.toUpperCase, AmazonSdkS3Client(client))
  }

  def fronts = {
    Await.result(client.config, 20.seconds).fronts
      .filter { case (_, v) => !v.isHidden.getOrElse(false) }.keySet.toSeq
  }

  def collections(front: String)(config: Config): Option[JsArray] = {
    val response: Response = Http.get(s"${config.nextGenApiUrl}$front/lite.json")

    if (response.code == 200) {
      (Json.parse(response.body.byteStream) \ "collections").asOpt[JsArray]
    } else {
      println(s"Could not retrieve editors picks for front: $front")
      None
    }
  }

}
