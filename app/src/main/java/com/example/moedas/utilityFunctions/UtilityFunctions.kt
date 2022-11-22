package com.example.moedas.utilityFunctions

import android.content.Intent
import android.graphics.Color
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.repository.RepositoryCurrencies
import com.example.moedas.userScreens.CambioScreen
import com.example.moedas.viewModel.ViewModelCurrency
import com.example.moedas.viewModel.ViewModelFactory
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