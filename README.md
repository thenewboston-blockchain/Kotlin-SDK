# Kotlin-SDK
Kotlin-SDK for thenewboston.

## Introduction
This is the Kotlin SDK with primary focus for Android and Desktop. This repository contains an android project which will demonstrate how to use 
the SDK. <br/><br/>
The library itself will solely contain the domain and data layers of thenewboston. The presentation layer will be the responsibility of <br/>
the host application (see our demo application). We'll be using Clean Architecture to structure the layers, dependencies and communication flow. <br/>
To not be coupled to any platform we'll avoid android specific libraries.
Besides Clean Architecture we'll use following libraries heavily:

1. Coroutines for Asynchronous / Parallel Processing
2. Ktor for Http Calls
3. JUnit for unit tests
4. Mockk for Mocking

By using the SDK you can create your own client to manage thenewboston currency, the next great thing!<br/>
As stated above, the SDK will not contain platform dependent details, such as storage engines or event / lifecycle handlers. <br/>
The reason is that eventually, we'll have the whole SDK compatible with Kotlin Multi Platform and have one SDK to rule them all.<br/>
## How to use
Nothing to say here yet :)