package com.example.bankapp.model


import com.google.gson.annotations.SerializedName

data class D(
    @SerializedName("market_cap_change")
    val marketCapChange: String?,
    @SerializedName("market_cap_change_pct")
    val marketCapChangePct: String?,
    @SerializedName("price_change")
    val priceChange: String?,
    @SerializedName("price_change_pct")
    val priceChangePct: String?,
    @SerializedName("volume")
    val volume: String?,
    @SerializedName("volume_change")
    val volumeChange: String?,
    @SerializedName("volume_change_pct")
    val volumeChangePct: String?
)