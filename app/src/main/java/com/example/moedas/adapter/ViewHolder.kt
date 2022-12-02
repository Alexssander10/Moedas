package com.example.moedas.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.utilityFunctions.UtilityFunctions
import java.text.NumberFormat
import java.util.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val typeCurrency = itemView.findViewById<TextView>(R.id.type_Currency)
    private val currencyPercentage = itemView.findViewById<TextView>(R.id.currency_Percentage)
    private val localBrazil = Locale("pt", "BR")
    private val brV: NumberFormat = NumberFormat.getCurrencyInstance(localBrazil)

    fun dadosMoeda(currencyStruture: ModelCurrency) {
        typeCurrency.text = currencyStruture.isoMoeda
        UtilityFunctions.colorVariation(currencyPercentage, currencyStruture)
        currencyPercentage.text = buildString {
            append(brV.format(currencyStruture.currencyVariation).toString().replace("R$", ""))
            append("%")
        }
    }
}