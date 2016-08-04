package com.gu.contentapi.models

import com.gu.contentapi.Config
import com.gu.contentapi.services.Http
import com.squareup.okhttp.Response
import play.api.libs.json.{ JsArray, JsValue, Json }

case class EditorsPick(front: String, collections: Seq[JsValue])

object EditorsPick {
  def apply(front: String)(config: Config): Option[EditorsPick] = {
    val response: Response = Http.get(s"${config.nextGenApiUrl}$front/lite.json")

    if (response.code == 200) {

      (Json.parse(response.body.byteStream) \ "collections").toOption
        .collect { case collections: JsArray => collections }
        .map(collections => EditorsPick(front, collections.value.take(25)))
        .filter(_.collections.nonEmpty) // we filter out collections that are empty so that in the event of a problem we don't overwrite them in Elasticsearch - discuss whether this is good or bad.

    } else {
      println(s"Could not retrieve editors picks for front: $front")
      None
    }

  }
}