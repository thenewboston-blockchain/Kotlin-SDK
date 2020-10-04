# Kotlin-SDK
Kotlin-SDK for thenewboston.

## Introduction
This is the Kotlin SDK with primary focus for Android and Desktop. This repository contains an android project which will demonstrate how to use 
the SDK. <br/><br/>
The library itself will solely contain the domain and data layers of thenewboston. The presentation layer will be the responsibility of <br/>
the host application (see our demo application). We'll be using Clean Architecture to structure the layers, dependencies and communication flow. <br/>
A rough architecture pattern would look like this: <br/>
Domain Layer | Data Layer                                                               
-------------|-----------
UseCase -> IRepository | RepositoryImpl -> DataSource (Local / Remote) --> Service (Http, Cache)  


Because of Dependency Injection the UseCase will know nothing about the actual repository.
(see https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html for more information, or read the book, it's nice)
Each layer would have its own layer model and mapping happens before data gets passed into the above layer, e.g.<br/>
A HTTP Service class does a http call and gets a JSON response, maps it to a DTO (DataTransferObject) and returns it to the DataSource. 
The DataSource has a mapper which transforms the DataSource to the entity (also called domain model). After mapping, the domain model
gets returned to the repository which then gets bubbled up to the calling UseCase. 
Repositories can not communicate between each other, if data from different repositories needs to be aggregated then a UseCase will call several <br/>
repositories and forge the model it requires.

To not be coupled to any platform we'll avoid android specific libraries.
Besides Clean Architecture we'll use following libraries heavily:

1. Coroutines for Asynchronous / Parallel Processing
2. Ktor for Http Calls
3. JUnit for unit tests
4. Mockk for Mocking

By using the SDK you can create your own client to manage thenewboston currency, the next great thing!<br/>
As stated above, the SDK will not contain platform dependent details, such as storage engines or event / lifecycle handlers. <br/>
The reason is that eventually, we'll have the whole SDK compatible with Kotlin Multi Platform and have one SDK to rule them all.<br/>
## How to get started
It's recommended to first go through https://www.thenewboston.com/guide/introduction <br/>
Once you understood the concept of thenewboston then check out https://github.com/thenewboston-developers/Account-Manager and <br/>
run the application. Create an account and play around a bit. Everything that happens behind the UI is what this SDK will cover. <br/>
## Outlook
Once we finish development of the SDK there are two paths we can pursue: 
1. Port the SDK to Kotlin Multiplatform to offer the SDK for iOS, Desktop and other Platforms
2. Build an App using Kotlin-SDK (the app module in this repository)
