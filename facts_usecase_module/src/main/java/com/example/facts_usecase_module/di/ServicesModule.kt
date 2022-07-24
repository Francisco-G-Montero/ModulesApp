package com.example.facts_usecase_module.di

import com.example.facts_usecase_module.data.coms.AppComs
import com.example.facts_usecase_module.data.repository.AppComsImpl
import com.example.facts_usecase_module.data.services.api.FactsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.publicapis.org/"

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Singleton
    @Provides
    fun provideFactsAPI(
        httpClient: OkHttpClient
    ): FactsApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactsApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideAppComsService(

    ): AppComs {
        return AppComsImpl()
    }
}