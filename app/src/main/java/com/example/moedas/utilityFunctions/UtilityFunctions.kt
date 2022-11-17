package com.example.moedas.utilityFunctions

import android.graphics.Color
import android.widget.TextView
import com.example.moedas.model.ModelCurrency
import java.text.DecimalFormat

object UtilityFunctions {


    fun colorVariation(currency_Percentage: TextView, moedas: ModelCurrency) {

        if (moedas.currencyVariation!! < 0) {
            currency_Percentage.setTextColor(Color.RED)
        } else if (moedas.currencyVariation > 0) {
            currency_Percentage.setTextColor(Color.GREEN)
        } else {
            currency_Percentage.setTextColor(Color.WHITE)
        }

        val percentage = DecimalFormat("#.##")
        currency_Percentage.text = percentage.format(moedas.currencyVariation).toString() + "%"
    }

    fun mapeiaNome(moedas: List<ModelCurrency?>): List<ModelCurrency?> {
        return moedas.map {
            it?.apply {
                it.isoMoeda =
                    when (it.nameCurrency) {
                        "Dollar" -> "USD"
                        "Euro" -> "EUR"
                        "Pound Sterling" -> "GBP"
                        "Argentine Peso" -> "ARS"
                        "Canadian Dollar" -> "CAD"
                        "Australian Dollar" -> "AUD"
                        "Japanese Yen" -> "JPY"
                        "Renminbi" -> "CNY"
                        "Bitcoin" -> "BTC"
                        else -> ""
                    }
            }
        }
    }
}