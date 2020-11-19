package com.thenewboston.config.repository

import com.thenewboston.config.datasource.ConfigDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.configdto.ConfigDTO
import javax.inject.Inject

class ConfigRepository @Inject constructor(private val dataSource: ConfigDataSource) {

    suspend fun fetchConfig(): Outcome<ConfigDTO> = dataSource.fetchConfig()
}
