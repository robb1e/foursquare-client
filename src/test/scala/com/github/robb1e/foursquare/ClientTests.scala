package com.github.robb1e.foursquare

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{GivenWhenThen, FeatureSpec}
import dispatch.{url, thread, Http}

class ClientTests extends FeatureSpec with ShouldMatchers with GivenWhenThen {

  implicit val config = new FoursquareApiConfig(){
      object ThreadSafeHttp extends Http with thread.Safety
      val http: Http = ThreadSafeHttp
      val clientId: String = "TFCH1VL5TB425OGQWLUSWT54P4ZN3YEPU015J1QU4NUTPSL5"
      val clientSecret: String  = "DZGRH25I1FBB13KRTRUZUH24GK3UAH3WLEOKDUJTZQZR0WB4"
      val categoryId: Option[String] = Some("4d4b7105d754a06376d81259")
  }

  feature("Searching Foursquare"){

    scenario("sanity check"){
        config.clientId should not be("")
        config.clientSecret should not be("")
    }

    scenario("with lat and long"){
      when("We search for a venue")
      val venues = Venues("40.7","-74")
      then("We should get some results")
      assert(venues.size > 0, "We didn't get any venues")
    }

    scenario("with lat and long and query"){
      when("We search for a venue")
      val venues = Venues("40.7","-74", "George")
      then("We should get some results")
      assert(venues.size > 0, "We didn't get any venues")
    }

  }
  
  feature("Venue") {
    scenario("retrieve a venue"){
      val venue = Venue("4ac518bef964a520faa220e3")
      venue.name should be("The Anchor")
      venue.categories.size should be(3)
      venue.primaryCategory.name should be("Pub")
      venue.primaryCategory.image should be("https://foursquare.com/img/categories/nightlife/pub_256.png")
      venue.tips should not be(None)
      venue.tips.get.count should be (30)
    }
  }

}
