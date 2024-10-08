package com.gu.contentapi.services

import com.amazonaws.auth.{ AWSCredentialsProviderChain, STSAssumeRoleSessionCredentialsProvider }
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.gu.contentapi.Config
import com.gu.facia.client.{ AmazonSdkS3Client, ApiClient }
import com.squareup.okhttp.Response
import play.api.libs.json.{ JsArray, Json }

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

object Facia {

  private val FaciaBucket = "facia-tool-store"

  private val client: ApiClient = {

    val cmsFrontsCredentials = new AWSCredentialsProviderChain(
      new ProfileCredentialsProvider("cmsFronts"),
      new STSAssumeRoleSessionCredentialsProvider.Builder(Config.aws.cmsFrontSTSRoleArn, "frontend").build)

    val client = AmazonS3ClientBuilder.standard()
      .withCredentials(cmsFrontsCredentials)
      .withEndpointConfiguration(new EndpointConfiguration(Config.aws.region.getServiceEndpoint("s3"), Config.aws.region.getName))
      .build

    ApiClient(FaciaBucket, Config.stage.toUpperCase, AmazonSdkS3Client(client))
  }

  def fronts = {
    Await.result(client.config, 20.seconds).fronts
      .filter { case (_, v) => !v.isHidden.getOrElse(false) }.keySet.toSeq
  }

  def collections(front: String): Option[JsArray] = {
    val response: Response = Http.get(s"${Config.nextGenApiUrl}$front/lite.json")

    if (response.code == 200) {
      val parsedResponse: Option[JsArray] = (Json.parse(response.body.byteStream) \ "collections").asOpt[JsArray]
      response.body.close()
      parsedResponse
    } else {
      println(s"Could not retrieve editors picks for front: $front")
      response.body.close()
      None
    }
  }

}
