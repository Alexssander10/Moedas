package com.example.moedas.userScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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

class HomeScreen : AppCompatActivity() {

    private lateinit var recyclerViewCurrencies: RecyclerView
    private lateinit var viewModelCurrency: ViewModelCurrency
    private val currencyAdapter by lazy {
        AdapterCurrency()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        initViewModel()

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
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun recyclerViewConfig() {
        recyclerViewCurrencies = findViewById(R.id.currencies_Rc_Home_Screen)
        recyclerViewCurrencies.layoutManager = LinearLayoutManager(this)
        recyclerViewCurrencies.adapter = currencyAdapter
        currencyAdapter.onClickActionCard = {
            Intent(this, CambioScreen::class.java).apply {
                putExtra("coinData", it)
                startActivity(this)
            }
        }
    }
}