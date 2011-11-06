package com.github.robb1e.foursquare

import dispatch.{url, thread, Http}
import net.liftweb.json.JsonParser._
import net.liftweb.json.DefaultFormats

case class Venues(venues: List[Venue])
case class Venue(id: String, name: String, location: Location)
case class Location(address: Option[String], lat: String, lng: String, postalCode: Option[String], distance: String)

trait FoursquareApiConfig {
    val http: Http
    val clientId: String
    val clientSecret: String
    val categoryId: Option[String]
}

object Venues {
    
  implicit val formats = DefaultFormats

  private val searchUri = "https://api.foursquare.com/v2/venues/search"
  private val uri = "https://api.foursquare.com/v2/venues/search?limit=20&client_id=%s&intent=checkin&client_secret=%s%s&v=20111101&ll=%s,%s"

  def apply(lat: String, long: String, query: String)(implicit config: FoursquareApiConfig): List[Venue] = {
    searchRequest(Map("ll" -> "%s,%s".format(lat, long), "query" -> query))      
  }

  def apply(lat: String, long: String)(implicit config: FoursquareApiConfig): List[Venue] = {
    searchRequest(Map("ll" -> "%s,%s".format(lat, long)))
  }
  
  private def searchRequest(params: Map[String, String])(implicit config: FoursquareApiConfig) = {
      val categoryParam: Map[String, String] = config.categoryId match {
          case Some(id) => Map("categoryId" -> id)
          case _ => Map()
      }
      val initParams = Map("limit" -> "20", "client_id" -> config.clientId, "intent" -> "checkin", "client_secret" -> config.clientSecret, "v" -> "20111101")
      val finalParams = initParams ++ params ++ categoryParam
      val request = url(searchUri) <<? finalParams
      val response = config.http(request as_str)
      val json = (parse(response) \ "response")
      json.extract[Venues].venues      
  }

}
