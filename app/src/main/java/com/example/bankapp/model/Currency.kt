package com.example.bankapp.model

data class Currency(
    val base: String,
    val commercialRatesList: List<CommercialRates>,
    val officialRatesList: List<OfficialRates>

) {

    data class CommercialRates(
        val currency: String,
        val buy: Double,
        val sell: Double,

        )

    data class OfficialRates(
        val currency: String,
        val value: Double

    )
}