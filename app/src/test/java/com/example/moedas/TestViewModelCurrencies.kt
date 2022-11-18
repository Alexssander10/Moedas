package com.example.moedas

import com.example.moedas.model.ModelCurrency
import com.example.moedas.model.TiposMoedasApi
import com.example.moedas.repository.RepositoryCurrencies
import com.example.moedas.viewModel.ViewModelCurrency
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class TestViewModelCurrencies : BaseTest() {

    private var api = mockk<RepositoryCurrencies>(relaxUnitFun = true)
    private var viewModel: ViewModelCurrency = ViewModelCurrency(api)


    @Test
    fun returNCurrenciesApi() {

        val resultado = TiposMoedasApi().apply {
            this.currencies.USD = ModelCurrency(
                nameCurrency = "Dollar",
                currencyVariation = 0.0,
                currencyPurchase = 2.0,
                currencySaleValue = 3.0,
                isoMoeda = "USD"
            )
        }
        val listaEsperada = listOfNotNull(
            resultado.currencies.USD,
            resultado.currencies.EUR,
            resultado.currencies.ARS,
            resultado.currencies.CAD,
            resultado.currencies.GBP,
            resultado.currencies.AUD,
            resultado.currencies.JPY,
            resultado.currencies.CNY,
            resultado.currencies.BTC)

        coEvery { api.loadCurrencies() } returns resultado

        viewModel.statusCurrencies()

        assertEquals(listaEsperada, viewModel.coinList.value)
    }

    @Test
    fun errorTestViewModelMoedas() {

        coEvery { api.loadCurrencies() } throws Exception("Ops, ocorreu um erro")

        viewModel.statusCurrencies()

        assertEquals("Ops, ocorreu um erro", viewModel.errorTest.value)
    }

}