package com.example.bankapp.di

import com.example.bankapp.crypto.CryptoRepository
import com.example.bankapp.api.CryptoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.nomics.com/v1/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesCryptoApi():CryptoApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoApi::class.java)

    @Singleton
    @Provides
    fun providesCryptoRepository(api: CryptoApi) = CryptoRepository(api)
}