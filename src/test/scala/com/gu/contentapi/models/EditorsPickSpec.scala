package com.gu.contentapi.models

import org.scalatest.{ FlatSpec, Matchers }
import play.api.libs.json.{ JsArray, Json }

import scala.io.Source._

class EditorsPickSpec extends FlatSpec with Matchers {

  it should "create an editors pick with the correct number of content items" in {
    val front = "uk"
    val collections = (Json.parse(fromFile("src/test/resources/uk-collections.json").mkString) \ "collections").as[JsArray]
    val editorsPick = EditorsPick(front, collections)
    val expectedContentItems = Json.parse(fromFile("src/test/resources/uk-expected-content-items.json").mkString).as[JsArray]

    editorsPick.front should be("uk")

    editorsPick.contentItems.size should be(25)
    (editorsPick.contentItems == expectedContentItems.value) should be(true)
  }

}
