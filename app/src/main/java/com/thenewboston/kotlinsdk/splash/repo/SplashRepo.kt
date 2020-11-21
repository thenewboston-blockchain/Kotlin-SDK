package com.thenewboston.kotlinsdk.splash.repo

interface SplashRepo {
    suspend fun getPrimaryValidatorIPAddress(): String?
}
