package com.example.moedas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency

class AdapterCurrency(var onClickActionCard: (ModelCurrency) -> Unit = {}) :
    RecyclerView.Adapter<ViewHolder>() {

    private val listOfCurrencies = mutableListOf<ModelCurrency?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_currencies_home_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listOfCurrencies[position]?.let { onClickScreen ->
            holder.dadosMoeda(onClickScreen)
            holder.itemView.setOnClickListener {
                onClickActionCard.invoke(onClickScreen)
            }
        }
    }

    override fun getItemCount(): Int = listOfCurrencies.size

    fun refresh(newList: List<ModelCurrency?>) {
        listOfCurrencies.clear()
        listOfCurrencies.addAll(newList)
        notifyDataSetChanged()
    }

}