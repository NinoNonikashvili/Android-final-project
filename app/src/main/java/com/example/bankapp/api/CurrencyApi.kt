package com.example.bankapp.api

import com.example.bankapp.model.ConvertInfo
import com.example.bankapp.model.Currency
import com.example.bankapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApi {

    @Headers("apikey: ${Constants.OFF_RATES_API_KEY}")
    @GET(Constants.OFF_RATES_ENDPOINT)
    suspend fun getOfficialRates(): Response<List<Currency.OfficialRate>>

    @Headers("apikey: ${Constants.COMM_CONVERT_API_KEY}")
    @GET(Constants.COMM_RATES_ENDPOINT)
    suspend fun getCommercialRates(): Response<Currency>

    @Headers("apikey: ${Constants.COMM_CONVERT_API_KEY}")
    @GET(Constants.CONVERT_RATES_ENDPOINT)
    suspend fun getConvertInfo(
        @Query("amount") amount: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<ConvertInfo>


}
