package com.gu.contentapi.models

import play.api.libs.json.{ JsArray, JsValue }

case class EditorsPick(front: String, contentItems: Seq[JsValue])

object EditorsPick {
  def apply(front: String, collections: JsArray): Option[EditorsPick] = {

    val first25ContentItemsFromAllCollections: Seq[JsValue] =
      collections.value
        .flatMap(c => (c \ "content").asOpt[JsArray].map(_.value))
        .flatten.take(25)

    Option(first25ContentItemsFromAllCollections)
      .map(item => EditorsPick(front, item))
      .filter(_.contentItems.nonEmpty) // we filter out collections that are empty so that in the event of a problem we don't overwrite them in Elasticsearch - discuss whether this is good or bad.
  }
}