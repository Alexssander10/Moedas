package com.example.moedas.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.utilityFunctions.UtilityFunctions

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val typeCurrency = itemView.findViewById<TextView>(R.id.type_Currency)
    private val currencyPercentage = itemView.findViewById<TextView>(R.id.currency_Percentage)

    fun dadosMoeda(currencyStruture: ModelCurrency) {
        typeCurrency.text = currencyStruture.isoMoeda
        currencyPercentage.text = currencyStruture.currencyVariation.toString()
        UtilityFunctions.colorVariation(currencyPercentage, currencyStruture)
    }
}