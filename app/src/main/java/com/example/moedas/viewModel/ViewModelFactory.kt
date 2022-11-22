package com.example.moedas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moedas.repository.RepositoryCurrencies

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val repository: RepositoryCurrencies) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewModelCurrency::class.java)) {
            ViewModelCurrency(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}