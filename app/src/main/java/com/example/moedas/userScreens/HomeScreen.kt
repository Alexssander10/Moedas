package com.example.moedas.userScreens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moedas.R
import com.example.moedas.viewModel.ViewModelCurrency
import com.example.moedas.adapter.AdapterCurrency
import com.example.moedas.repository.RepositoryCurrencies
import com.example.moedas.viewModel.ViewModelFactory


class HomeScreen : BaseActivity() {

    private lateinit var recyclerViewCurrencies: RecyclerView
    private lateinit var viewModelCurrency: ViewModelCurrency
    private val currencyAdapter by lazy {
        AdapterCurrency()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)
        initViewModel()
        configMenuToolbar(
            findViewById(R.id.toolbarHomeScreen),
            findViewById(R.id.TitleMenuToolbar),getString(R.string.currenciesToolbarTitleMoedas),
            findViewById(R.id.btn_back_toolbars_screens)
        )
    }

    private fun initViewModel() {
        viewModelCurrency = ViewModelProvider(
            this,
            ViewModelFactory(RepositoryCurrencies())
        )[ViewModelCurrency::class.java]

        viewModelCurrency.coinList.observe(this) {
            currencyAdapter.refresh(it)
            recyclerViewConfig()
        }

        viewModelCurrency.statusCurrencies()

        viewModelCurrency.errorTest.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun recyclerViewConfig() {
        recyclerViewCurrencies = findViewById(R.id.currencies_Rc_Home_Screen)
        recyclerViewCurrencies.layoutManager = LinearLayoutManager(this)
        recyclerViewCurrencies.adapter = currencyAdapter
        currencyAdapter.onClickActionCard = {
            Intent(this, CambioScreen::class.java).apply {
                //coinData = passa os dados da moeda
                putExtra(getString(R.string.dadosDaMoeda), it)
                startActivity(this)
            }
        }
    }
}