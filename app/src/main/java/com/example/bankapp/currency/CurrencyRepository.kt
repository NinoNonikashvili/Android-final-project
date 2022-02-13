package com.example.bankapp.currency

import com.example.bankapp.model.Currency
import com.example.bankapp.util.Resource

interface CurrencyRepository {

    suspend fun getOfficialRates(): Resource<List<Currency.OfficialRate>>

    suspend fun getCommercialRates(): Resource<Currency>
}