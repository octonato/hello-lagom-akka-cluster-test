package com.example.helloakkacluster.impl

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class HelloakkaclusterEntitySpec extends WordSpec with Matchers with BeforeAndAfterAll {

  private val system = ActorSystem("HelloakkaclusterEntitySpec",
    JsonSerializerRegistry.actorSystemSetupFor(HelloakkaclusterSerializerRegistry))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private def withTestDriver(block: PersistentEntityTestDriver[HelloakkaclusterCommand[_], HelloakkaclusterEvent, HelloakkaclusterState] => Unit): Unit = {
    val driver = new PersistentEntityTestDriver(system, new HelloakkaclusterEntity, "hello-akka-cluster-1")
    block(driver)
    driver.getAllIssues should have size 0
  }

  "hello-akka-cluster entity" should {

    "say hello by default" in withTestDriver { driver =>
      val outcome = driver.run(Hello("Alice"))
      outcome.replies should contain only "Hello, Alice!"
    }

    "allow updating the greeting message" in withTestDriver { driver =>
      val outcome1 = driver.run(UseGreetingMessage("Hi"))
      outcome1.events should contain only GreetingMessageChanged("Hi")
      val outcome2 = driver.run(Hello("Alice"))
      outcome2.replies should contain only "Hi, Alice!"
    }

  }
}
