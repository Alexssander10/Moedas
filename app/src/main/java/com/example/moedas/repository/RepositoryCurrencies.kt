package com.example.moedas.repository

import com.example.moedas.model.TiposMoedasApi
import com.example.moedas.retrofit.Retrofit

class RepositoryCurrencies {

    val  services = Retrofit().currenciesServiceRetrofit()

    suspend fun loadCurrencies(): TiposMoedasApi {

        return services.MapperCurrencies()
    }

}