package com.example.moedas.retrofit.apicurrenciesservice

import com.example.moedas.model.TiposMoedasApi
import retrofit2.http.GET

interface ApiCurrencies {
    @GET("finance?fields=only_results,currencies&key=4c5ab806")
    suspend fun MapperCurrencies(): TiposMoedasApi
}
