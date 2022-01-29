package com.example.bankapp.model


import com.google.gson.annotations.SerializedName

data class CryptoDataItem(
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("1d")
    val d: D?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("logo_url")
    val logoUrl: String?,
    @SerializedName("market_cap")
    val marketCap: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("price_date")
    val priceDate: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("symbol")
    val symbol: String?
)