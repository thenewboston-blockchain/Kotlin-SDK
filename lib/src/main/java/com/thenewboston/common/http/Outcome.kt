package com.thenewboston.common.http

/**
 * Based on Philipp Hauer's post: https://phauer.com/2019/sealed-classes-exceptions-kotlin/#sealed-classes-to-the-rescue
 */
sealed class Outcome<out T : Any> {
    data class Success<out T : Any>(val value: T) : Outcome<T>()
    data class Error(val message: String, val cause: Exception? = null) : Outcome<Nothing>()
}

/**
 * Allows enforcing exhaustive `when` statements:
 *
 * when (val outcome = someRequest()) {
 *   is Outcome.Success -> doSomethingWith(outcome.result)
 * }.exhaustive // -> fails because Outcome.Error is not handled
 *
 *
 */
val <T> T.exhaustive: T
    get() = this
