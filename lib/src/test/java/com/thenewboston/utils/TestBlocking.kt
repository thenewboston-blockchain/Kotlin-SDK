package com.thenewboston.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

/**
 * JUnit will ignore test cases that don't return Unit.
 * Remembering to use runBlocking<Unit> is annoying and error-prone so this method encapsulates it.
 */
internal inline fun testBlocking(crossinline test: suspend CoroutineScope.() -> Unit) = runBlocking<Unit> { test() }
