package com.example.moedas.utilityFunctions

import com.example.moedas.model.ModelCurrency

object SingletonValuesCurrencies {

    var buyOrSell: String? = null
    var walletValue = 50.00
    var USD = 10
    var EUR = 8
    var ARS = 2
    var CAD = 5
    var GBP = 7
    var AUD = 1
    var JPY = 9
    var CNY = 2
    var BTC = 1

    fun bindValues(moeda: ModelCurrency) {

        when {
            moeda.isoMoeda.equals("USD") -> moeda.isoValueCurrencie = USD
            moeda.isoMoeda.equals("EUR") -> moeda.isoValueCurrencie = EUR
            moeda.isoMoeda.equals("GBP") -> moeda.isoValueCurrencie = GBP
            moeda.isoMoeda.equals("ARS") -> moeda.isoValueCurrencie = ARS
            moeda.isoMoeda.equals("CAD") -> moeda.isoValueCurrencie = CAD
            moeda.isoMoeda.equals("AUD") -> moeda.isoValueCurrencie = AUD
            moeda.isoMoeda.equals("JPY") -> moeda.isoValueCurrencie = JPY
            moeda.isoMoeda.equals("CNY") -> moeda.isoValueCurrencie = CNY
            moeda.isoMoeda.equals("BTC") -> moeda.isoValueCurrencie = BTC
        }
    }

    fun afterLinkingValues(moeda: ModelCurrency){
        when{
            moeda.isoMoeda.equals("USD") -> USD = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("EUR") -> EUR = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("GBP") -> GBP = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("ARS") -> ARS = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("CAD") -> CAD = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("AUD") -> AUD = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("JPY") -> JPY = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("CNY") -> CNY = moeda.isoValueCurrencie
            moeda.isoMoeda.equals("BTC") -> BTC = moeda.isoValueCurrencie
        }
    }
}