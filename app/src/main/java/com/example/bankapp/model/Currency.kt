package com.example.bankapp.model


data class Currency(
    val base: String,
    val commercialRatesList: List<CommercialRate>,
    val officialRatesList: List<OfficialRate>

) {

    data class CommercialRate(
        val currency: String,
        val buy: Double,
        val sell: Double,

        )

    data class OfficialRate(
        val currency: String,
        val value: Double

    )
}
