package com.gu.contentapi.services

import com.squareup.okhttp.{ OkHttpClient, Request, Response }

object Http {
  private lazy val httpClient: OkHttpClient = new OkHttpClient()

  def get(url: String): Response =
    Http.httpClient.newCall(new Request.Builder().url(url).build).execute()

}
