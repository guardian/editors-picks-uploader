package com.gu.contentapi.models

import org.joda.time.DateTime
import play.api.libs.json.{ JsValue, Json }

case class Item(id: String, content: JsValue)

object Item {
  implicit def ItemFormats = Json.format[Item]
}

case class NotificationBody(
  id: String,
  bodyType: String = "Document",
  itemType: String,
  item: Item
)

object NotificationBody {
  implicit def NotificationBodyFormats = Json.format[NotificationBody]
}

case class Notification(
  version: Int = 1,
  `type`: String,
  timestamp: DateTime = new DateTime,
  body: NotificationBody
)

object Notification {

  implicit def NotificationFormats = Json.format[Notification]

  def create(editorsPick: EditorsPick): Notification = {
    val id = editorsPick.front
    val itemType = "editors-picks"
    val item = Item(id, Json.toJson(editorsPick.contentItems))

    val notificationBody = NotificationBody(id, itemType = itemType, item = item)

    Notification(`type` = "indexable-editors-picks", body = notificationBody)
  }

}
