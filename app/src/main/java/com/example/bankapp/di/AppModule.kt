package com.example.bankapp.di

import com.example.bankapp.repositories.CryptoRepository
import com.example.bankapp.api.CryptoApi
import com.example.bankapp.api.CurrencyApi
import com.example.bankapp.repositories.CurrencyRepository
import com.example.bankapp.repositories.CurrencyRepositoryImpl
import com.example.bankapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//private const val BASE_URL = "https://api.nomics.com/v1/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideCurrencyApi(okHttpClient: OkHttpClient): CurrencyApi = Retrofit.Builder()
        .baseUrl(Constants.CURRENCY_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .client(okHttpClient)
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository = CurrencyRepositoryImpl(api)


    @Singleton
    @Provides
    fun providesCryptoApi():CryptoApi = Retrofit.Builder()
        .baseUrl(Constants.CRYPTO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoApi::class.java)

    @Singleton
    @Provides
    fun providesCryptoRepository(api: CryptoApi) = CryptoRepository(api)
}