package com.example.moedas.utilityFunctions

import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import com.example.moedas.model.ModelCurrency
import java.text.NumberFormat
import java.util.*

object UtilityFunctions {


    fun colorVariation(currency_Percentage: TextView, moedas: ModelCurrency) {

        if (moedas.currencyVariation!! < 0) {
            currency_Percentage.setTextColor(Color.RED)
        } else if (moedas.currencyVariation > 0) {
            currency_Percentage.setTextColor(Color.GREEN)
        } else {
            currency_Percentage.setTextColor(Color.WHITE)
        }
        val localBrazil = Locale("pt", "BR")
        val brV: NumberFormat = NumberFormat.getCurrencyInstance(localBrazil)
        currency_Percentage.text = buildString {
            append(brV.format(moedas.currencyVariation).replace("R$", ""))
            append("%")
        }
    }

    fun mapperName(moedas: List<ModelCurrency?>): List<ModelCurrency?> {
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