User Service
----------------
This is a spring boot microservice which has **USER** related CRUD REST APIs.
  * This microservice registers and is discoverable via **Discover-Service (Eureka Server)**.
  * It uses strucutered Controller-Service-Dao/Repo architecture, designed for interfaces.
  * It uses **MongoDb** as persistence layer and **Spring Data** as ORM.
  * **AOP** is used for logging across application
  * Uses **Feign Client** , which uses ribbon + eureka to call another microservice
