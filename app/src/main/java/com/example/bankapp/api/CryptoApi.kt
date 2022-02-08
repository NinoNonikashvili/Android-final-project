package com.example.bankapp.api

import com.example.bankapp.model.CryptoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("currencies/ticker")
    suspend fun getCryptoData(
        @Query("key")
        key : String = "m_35f92c985560058c07533ecbd1063e7f0daa183d",
        @Query("interval")
        interval : String = "1d",
        @Query("convert")
        convert : String ="USD"
        ): Response<CryptoData>
}