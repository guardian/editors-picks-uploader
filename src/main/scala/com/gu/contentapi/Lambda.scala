package com.gu.contentapi

import java.util.{ Map => JMap }

import com.amazonaws.services.lambda.runtime.{ Context, RequestHandler }
import com.gu.contentapi.models.{ EditorsPick, Notification }
import com.gu.contentapi.services.{ Facia, S3, SNS }
import play.api.libs.json.Json

import scala.util.{ Failure, Success }

class Lambda
    extends RequestHandler[JMap[String, Object], Unit] {

  override def handleRequest(event: JMap[String, Object], context: Context): Unit = {

    val config = new Config(context)
    val facia = new Facia(config)
    val sns = new SNS(config)

    for {
      editorsPick <- facia.fronts.flatMap(EditorsPick(_)(config))
    } yield {
      val notification = Notification.create(editorsPick)

      sns.publish(config.aws.topicArn, Json.stringify(Json.toJson(notification))) match {
        case Success(_) => println(s"Successfully published editors picks for front: ${notification.body.id}")
        case Failure(e) => println(s"Could not publish editors picks for front: ${notification.body.id}, $e")
      }
    }

  }

}
