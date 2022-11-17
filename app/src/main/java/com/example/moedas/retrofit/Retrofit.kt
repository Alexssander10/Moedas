package com.example.moedas.retrofit

import com.example.moedas.retrofit.apicurrenciesservice.ApiCurrencies
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    private val URL_BASE = "https://api.hgbrasil.com/"

    fun currenciesServiceRetrofit(): ApiCurrencies {

        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiCurrencies::class.java)
    }
}