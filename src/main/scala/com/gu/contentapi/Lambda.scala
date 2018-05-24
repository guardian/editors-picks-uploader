package com.gu.contentapi

import java.util.{ Map => JMap }

import com.amazonaws.services.lambda.runtime._
import com.gu.contentapi.models.{ EditorsPick, Notification }
import com.gu.contentapi.services.{ Facia, SNS }
import play.api.libs.json.Json

import scala.util.{ Failure, Success }

class Lambda
  extends RequestHandler[JMap[String, Object], Unit] {

  override def handleRequest(event: JMap[String, Object], context: Context): Unit = {

    for {
      front <- Facia.fronts
      collections <- Facia.collections(front)
      notification = Notification.create(EditorsPick(front, collections))
    } yield {
      SNS.publish(Config.aws.topicArn, Json.stringify(Json.toJson(notification))) match {
        case Success(_) => println(s"Successfully published editors picks for front: ${notification.body.id}")
        case Failure(e) => println(s"Could not publish editors picks for front: ${notification.body.id}, $e")
      }
    }

  }

}
