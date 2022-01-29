package com.example.bankapp.model


import com.google.gson.annotations.SerializedName

data class D(

    @SerializedName("price_change_pct")
    val priceChangePct: String?,
    @SerializedName("volume")
    val volume: String?,
    @SerializedName("volume_change_pct")
    val volumeChangePct: String?
)