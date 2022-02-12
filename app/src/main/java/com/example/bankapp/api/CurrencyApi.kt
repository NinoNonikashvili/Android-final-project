package com.example.bankapp.api

import com.example.bankapp.model.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CurrencyApi {

    @Headers("apikey: 3WMlTbsOnYF3PSx3jv3GemyAG5NMZgPR")
    @GET("/v1/exchange-rates/nbg")
    suspend fun getOfficialRates(): Response<List<Currency.OfficialRates>>


    @Headers("apikey: I3Rz8QXBVPofKtucsenkW7g2n7aDwqBg")
    @GET("/v1/exchange-rates/commercial")
    suspend fun getCommercialRates(): Response<Currency>
}