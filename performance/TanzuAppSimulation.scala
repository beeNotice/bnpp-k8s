package com.tanzu.perf

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TanzuAppSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")

  // A scenario is a chain of requests and pauses
  val scn = scenario("Main scenario")
    .exec(
      http("people")
        .get("/people")
        .check(status.is(200))
    )
    .exec(
      http("people/1")
        .get("/people/1")
        .check(status.is(200))
    )
    .exec(
      http("people/10")
        .get("/people/10")
        .check(status.is(404))
    )

  // https://gatling.io/docs/current/general/simulation_setup/  
  setUp(scn.inject(rampUsers(500).during(30.seconds)).protocols(httpProtocol))

}
