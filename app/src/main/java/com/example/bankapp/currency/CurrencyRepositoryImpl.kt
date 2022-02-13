package com.example.bankapp.currency

import com.example.bankapp.api.CurrencyApi
import com.example.bankapp.currency.CurrencyRepository
import com.example.bankapp.model.Currency
import com.example.bankapp.util.Resource
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyApi: CurrencyApi) :
    CurrencyRepository {


    override suspend fun getOfficialRates(): Resource<List<Currency.OfficialRate>> {
        return try {
            val response = currencyApi.getOfficialRates()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Throwable) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    override suspend fun getCommercialRates(): Resource<Currency> {
        return try {
            val response = currencyApi.getCommercialRates()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())

            }
        } catch (e: Throwable) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}