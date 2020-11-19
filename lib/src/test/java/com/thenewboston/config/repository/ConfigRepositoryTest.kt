package com.thenewboston.config.repository

import com.thenewboston.common.http.Outcome
import com.thenewboston.config.datasource.ConfigDataSource
import com.thenewboston.utils.Mocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class ConfigRepositoryTest {

    @MockK
    lateinit var configDataSource: ConfigDataSource

    private lateinit var repository: ConfigRepository

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        repository = ConfigRepository(configDataSource)
    }

    @Test
    fun `verify result is error`() = runBlockingTest {
        coEvery { configDataSource.fetchConfig() } returns Outcome.Error("", IOException())

        assertTrue(repository.fetchConfig() is Outcome.Error)
    }

    @Test
    fun `verify result is success`() = runBlockingTest {
        coEvery { configDataSource.fetchConfig() } returns Outcome.Success(Mocks.config())

        assertTrue(repository.fetchConfig() is Outcome.Success)
    }
}
