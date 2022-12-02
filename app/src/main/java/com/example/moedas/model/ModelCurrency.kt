package com.example.moedas.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ModelCurrency(
    @SerializedName("name")
    val nameCurrency: String? = null,
    @SerializedName("variation")
    val currencyVariation: Double? = null,
    @SerializedName("buy")
    val currencyPurchase: Double? = null,
    @SerializedName("sell")
    val currencySaleValue: Double?= null,
    var isoMoeda: String = "",
    /*var isoValueCurrencie: Int = 0*/
) : Serializable

