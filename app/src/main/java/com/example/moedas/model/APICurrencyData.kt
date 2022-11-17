package com.example.moedas.model

import com.google.gson.annotations.SerializedName

data class TiposMoedasApi(
    @SerializedName("currencies")
    var currencies: ResultData = ResultData()
)

data class ResultData(
    @SerializedName("source")
    var source: String = "",
    @SerializedName("USD")
    var USD: ModelCurrency? = null,
    @SerializedName("EUR")
    var EUR: ModelCurrency? = null,
    @SerializedName("GBP")
    var GBP: ModelCurrency? = null,
    @SerializedName("ARS")
    var ARS: ModelCurrency? = null,
    @SerializedName("CAD")
    var AUD: ModelCurrency? = null,
    @SerializedName("AUD")
    var JPY: ModelCurrency? = null,
    @SerializedName("JPY")
    var CNY: ModelCurrency? = null,
    @SerializedName("CNY")
    var BTC: ModelCurrency? = null,
    @SerializedName("BTC")
    var CAD: ModelCurrency? = null
)