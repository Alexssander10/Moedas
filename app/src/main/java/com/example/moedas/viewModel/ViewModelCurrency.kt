package com.example.moedas.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.moedas.model.ModelCurrency
import com.example.moedas.repository.RepositoryCurrencies
import com.example.moedas.utilityFunctions.UtilityFunctions.mapperName
import kotlinx.coroutines.launch


class ViewModelCurrency(private val repositoryCurrencies: RepositoryCurrencies) :
    ViewModelCoroutine() {

    val coinList = MutableLiveData<List<ModelCurrency?>>()
    val errorTest = MutableLiveData<String>()

    fun statusCurrencies(): MutableLiveData<List<ModelCurrency?>> {
        launch {
            try {
                val call = repositoryCurrencies.loadCurrencies()
                val coinListLaunch = mapperName(
                    listOfNotNull(
                        call.currencies.USD,
                        call.currencies.EUR,
                        call.currencies.ARS,
                        call.currencies.CAD,
                        call.currencies.GBP,
                        call.currencies.AUD,
                        call.currencies.JPY,
                        call.currencies.CNY,
                        call.currencies.BTC
                    )
                )
                coinList.postValue(coinListLaunch)
            } catch (e: Exception) {
                errorTest.postValue("Ops, ocorreu um erro")
            }
        }
        return coinList
    }
}