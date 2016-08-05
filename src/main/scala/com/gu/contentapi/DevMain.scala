package com.gu.contentapi

import java.util

import com.amazonaws.services.lambda.runtime.{ ClientContext, CognitoIdentity, Context, LambdaLogger }

import scala.concurrent.duration.Duration

/**
 * Harness for running the Lambda on a dev machine.
 */
object DevMain extends App {

  val fakeScheduledEvent = new util.HashMap[String, Object]()

  val start = System.nanoTime()
  new Lambda().handleRequest(fakeScheduledEvent, new TestContext)
  val end = System.nanoTime()
  Console.err.println(s"Time taken: ${Duration.fromNanos(end - start).toMillis} ms")

}

class TestContext extends Context {
  override def getFunctionName: String = "editors-picks-uploader-CODE"

  override def getIdentity: CognitoIdentity = throw new UnsupportedOperationException
  override def getLogStreamName: String = throw new UnsupportedOperationException
  override def getClientContext: ClientContext = throw new UnsupportedOperationException
  override def getLogger: LambdaLogger = throw new UnsupportedOperationException
  override def getMemoryLimitInMB: Int = throw new UnsupportedOperationException
  override def getInvokedFunctionArn: String = throw new UnsupportedOperationException
  override def getRemainingTimeInMillis: Int = throw new UnsupportedOperationException
  override def getAwsRequestId: String = throw new UnsupportedOperationException
  override def getFunctionVersion: String = throw new UnsupportedOperationException
  override def getLogGroupName: String = throw new UnsupportedOperationException
}
