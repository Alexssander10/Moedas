package com.example.moedas.utilityFunctions

object SingletonValuesCurrencies {

    var buyOrSell: String? = null
    var walletValue = 50.00

    var hashMap = HashMap<String, Int>().apply {
        put("USD", 10)
        put("EUR", 8)
        put("ARS", 2)
        put("CAD", 5)
        put("GBP", 7)
        put("AUD", 1)
        put("JPY", 9)
        put("CNY", 2)
        put("BTC", 1)
    }

    fun takeValueHashMap(isoMoeda: String): Int {
        hashMap[isoMoeda].let { quantidade ->
            if (quantidade != null) return quantidade
            else return 0
        }
    }

    fun valueTransfModif(
        coin: String,
        successfulTransaction: String,
        value: Int
    ){
        var amountSSingleton = hashMap[coin]
        if (amountSSingleton != null) {
            if (successfulTransaction == "Buy") {
                amountSSingleton += value
            } else {
                amountSSingleton -= value
            }
            hashMap[coin] = amountSSingleton
        }
    }
}