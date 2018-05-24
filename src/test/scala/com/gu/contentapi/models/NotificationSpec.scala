package com.gu.contentapi.models

import org.joda.time.{ DateTime, DateTimeUtils }
import org.scalatest.{ FlatSpec, Matchers }
import play.api.libs.json.{ JsArray }

class NotificationSpec extends FlatSpec with Matchers {

  it should "Create a notification" in {
    val now = new DateTime
    DateTimeUtils.setCurrentMillisFixed(now.getMillis)

    val editorsPick = EditorsPick(front = "uk", contentItems = Nil)

    Notification.create(editorsPick) should be(
      Notification(
        version = 1,
        `type` = "indexable-editors-picks",
        timestamp = now,
        body = NotificationBody("uk", "Document", "editors-picks", Item("uk", JsArray()))))

    DateTimeUtils.setCurrentMillisSystem()
  }

}
