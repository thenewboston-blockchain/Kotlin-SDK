package com.thenewboston.api.primaryvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PatchDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.makeApiCall
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class PrimaryDataSource @Inject constructor(
    private val getDataSource: GetDataSource,
    private val patchDataSource: PatchDataSource,
    private val postDataSource: PostDataSource
) {

    suspend fun fetchPrimaryValidatorDetails() = makeApiCall(
        call = { getDataSource.primaryValidatorDetails() },
        errorMessage = "Failed to retrieve primary validator details"
    )
}
