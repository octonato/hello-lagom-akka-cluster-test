package com.example.helloakkacluster.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.helloakkacluster.api.HelloakkaclusterService
import com.softwaremill.macwire._
import com.lightbend.lagom.scaladsl.playjson.EmptyJsonSerializerRegistry

class HelloakkaclusterLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new HelloakkaclusterApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HelloakkaclusterApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[HelloakkaclusterService])
}

abstract class HelloakkaclusterApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  // val config = configuration.underlying
  
  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[HelloakkaclusterService](wire[HelloakkaclusterServiceImpl])

  // // Register the JSON serializer registry
  // override lazy val jsonSerializerRegistry = EmptyJsonSerializerRegistry
  
  
  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = HelloakkaclusterSerializerRegistry

  // Register the Hello persistent entity
  persistentEntityRegistry.register(wire[HelloakkaclusterEntity])

}
