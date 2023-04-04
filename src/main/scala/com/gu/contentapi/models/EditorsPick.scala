package com.gu.contentapi.models

import play.api.libs.json.{ JsArray, JsValue }

case class EditorsPick(front: String, contentItems: Seq[JsValue])

object EditorsPick {
  def apply(front: String, collections: JsArray): EditorsPick = {

    val first25ContentItemsFromAllCollections: Seq[JsValue] =
      collections.value
        .flatMap(c => (c \ "content").asOpt[JsArray].map(_.value))
        .flatten.take(25).toSeq

    EditorsPick(front, first25ContentItemsFromAllCollections)
  }
}