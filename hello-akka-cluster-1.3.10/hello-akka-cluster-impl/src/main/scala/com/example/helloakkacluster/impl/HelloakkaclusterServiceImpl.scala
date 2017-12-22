package com.example.helloakkacluster.impl

import com.example.helloakkacluster.api
import com.example.helloakkacluster.api.{HelloakkaclusterService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.typesafe.config._
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Implementation of the HelloakkaclusterService.
  */
class HelloakkaclusterServiceImpl(persistentEntityRegistry: PersistentEntityRegistry, config: Config) extends HelloakkaclusterService {

  override def hello(id: String) = ServiceCall { _ =>
    
    // Look up the Hello entity for the given ID.
    val ref = persistentEntityRegistry.refFor[HelloakkaclusterEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id)).map { greeting => 
      s"""
      | greeting       = $greeting
      | additional ser = ${config.getString("akka.actor.enable-additional-serialization-bindings")}
      | remote port    = ${config.getInt("akka.remote.netty.tcp.port")}
      | version        = 1.3.10
      """.stripMargin
    }
    
    
  }

}
