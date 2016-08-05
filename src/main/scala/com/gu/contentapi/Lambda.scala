package com.gu.contentapi

import java.util.{ Map => JMap }

import com.amazonaws.services.lambda.runtime._
import com.gu.contentapi.models.{ EditorsPick, Notification }
import com.gu.contentapi.services.{ Facia, SNS }
import play.api.libs.json.{ Json }

import scala.util.{ Failure, Success }

class Lambda
    extends RequestHandler[JMap[String, Object], Unit] {

  override def handleRequest(event: JMap[String, Object], context: Context): Unit = {

    val config = new Config(context.getFunctionName)
    val facia = new Facia(config)
    val sns = new SNS(config)

    for {
      front <- facia.fronts
      collections <- facia.collections(front)(config)
      editorsPick <- EditorsPick(front, collections)
      notification = Notification.create(editorsPick)
    } yield {
      sns.publish(config.aws.topicArn, Json.stringify(Json.toJson(notification))) match {
        case Success(_) => println(s"Successfully published editors picks for front: ${notification.body.id}")
        case Failure(e) => println(s"Could not publish editors picks for front: ${notification.body.id}, $e")
      }
    }

  }

}
