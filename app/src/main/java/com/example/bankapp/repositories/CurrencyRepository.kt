package com.example.bankapp.repositories

import com.example.bankapp.model.ConvertInfo
import com.example.bankapp.model.Currency
import com.example.bankapp.util.Resource

interface CurrencyRepository {

    suspend fun getOfficialRates(): Resource<List<Currency.OfficialRate>>

    suspend fun getCommercialRates(): Resource<Currency>

    suspend fun getConvertInfo(
        amount: String,
        from: String,
        to: String
    ): Resource<ConvertInfo>


}