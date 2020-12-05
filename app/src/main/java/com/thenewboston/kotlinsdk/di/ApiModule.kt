package com.thenewboston.kotlinsdk.di
import android.content.Context
import com.thenewboston.kotlinsdk.PRIMARY_VALIDATOR
import com.thenewboston.kotlinsdk.network.apis.ApiClient
import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.network.apis.ValidatorsApi
import com.thenewboston.kotlinsdk.utils.TinyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBankApi(): BankApis = ApiClient.defaultBankClient.create(BankApis::class.java)

    @Provides
    @Singleton
    fun provideValidatorApi(@ApplicationContext appContext: Context): ValidatorsApi = ApiClient
        .validatorsClient(
            TinyDB.getDataFromLocal(appContext, PRIMARY_VALIDATOR)!!
        )
        .create(ValidatorsApi::class.java)
}
