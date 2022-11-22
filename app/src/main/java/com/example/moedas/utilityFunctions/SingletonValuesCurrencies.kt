package com.example.moedas.utilityFunctions

import com.example.moedas.model.ModelCurrency

object SingletonValuesCurrencies {

    var buyOrSell: String? = null
    var walletValue = 50.00
    private var USD = 10
    private var EUR = 8
    private var ARS = 2
    private var CAD = 5
    private var GBP = 7
    private var AUD = 1
    private var JPY = 9
    private var CNY = 2
    private var BTC = 1

    fun bindValues(moeda: ModelCurrency) {
        when (moeda.isoMoeda) {
            "USD" -> moeda.isoValueCurrencie = USD
            "EUR" -> moeda.isoValueCurrencie = EUR
            "GBP" -> moeda.isoValueCurrencie = GBP
            "ARS" -> moeda.isoValueCurrencie = ARS
            "CAD" -> moeda.isoValueCurrencie = CAD
            "AUD" -> moeda.isoValueCurrencie = AUD
            "JPY" -> moeda.isoValueCurrencie = JPY
            "CNY" -> moeda.isoValueCurrencie = CNY
            "BTC" -> moeda.isoValueCurrencie = BTC
        }
    }

    fun afterLinkingValues(moeda: ModelCurrency) {
        when (moeda.isoMoeda) {
            "USD" -> USD = moeda.isoValueCurrencie
            "EUR" -> EUR = moeda.isoValueCurrencie
            "GBP" -> GBP = moeda.isoValueCurrencie
            "ARS" -> ARS = moeda.isoValueCurrencie
            "CAD" -> CAD = moeda.isoValueCurrencie
            "AUD" -> AUD = moeda.isoValueCurrencie
            "JPY" -> JPY = moeda.isoValueCurrencie
            "CNY" -> CNY = moeda.isoValueCurrencie
            "BTC" -> BTC = moeda.isoValueCurrencie
        }
    }
}