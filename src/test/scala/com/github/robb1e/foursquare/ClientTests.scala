package com.weavemadethis.foursquare

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{GivenWhenThen, FeatureSpec}
import dispatch.{url, thread, Http}

class ClientTests extends FeatureSpec with ShouldMatchers with GivenWhenThen {

  implicit val config = new FoursquareApiConfig(){
      object ThreadSafeHttp extends Http with thread.Safety
      val http: Http = ThreadSafeHttp
      val clientId: String = ""
      val clientSecret: String  = ""
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

  }

}
