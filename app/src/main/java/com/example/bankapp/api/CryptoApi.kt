package com.example.bankapp.api

import com.example.bankapp.model.CryptoData
import com.example.bankapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET(Constants.CRYPTO_API_ENDPOINT)
    suspend fun getCryptoData(
        @Query("key")
        key : String = Constants.CRYPTO_API_KEY,
        @Query("interval")
        interval : String = "1d",
        @Query("convert")
        convert : String ="USD",
        @Query("per-page")
        perPage:String = "50",
        @Query("page")
        page:String = "1"
        ): Response<CryptoData>
}