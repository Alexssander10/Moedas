package com.example.moedas.userScreens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.buyOrSell
import java.text.NumberFormat
import java.util.*

class BuyAndSellScreen : BaseActivity() {

    private lateinit var successfullyTransactionMessageTxt: TextView
    private lateinit var homeScreenBtn: Button
    private var cambioMoedas: ModelCurrency? = null
    private val congratulationMessage = StringBuilder()
    private val localBrazil = Locale("pt", "BR")
    private val br : NumberFormat = NumberFormat.getCurrencyInstance(localBrazil)
    private lateinit var buySellStatusToolbar : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_and_sell_screen)

        linkID()
        toolbar()
        configMenuToolbar(
            findViewById(R.id.toolbar_buy_and_sell),
            findViewById(R.id.TitleMenuToolbar),
            buySellStatusToolbar,
            findViewById(R.id.btn_back_toolbars_screens)
        )
        finishTransaction()
        textViewConfigs()
    }

    private fun toolbar() {
        val toolbarStatus = intent.getBooleanExtra(getString(R.string.Buy), true)
        buySellStatusToolbar = if (toolbarStatus) {
            "Comprar"
        } else {
            "Vender"
        }
    }

    private fun linkID() {
        successfullyTransactionMessageTxt = findViewById(R.id.txT_Congratulation)
        homeScreenBtn = findViewById(R.id.btn_back_home_screen)
    }

    private fun textViewConfigs() {
        TextViewCompat.setAutoSizeTextTypeWithDefaults(successfullyTransactionMessageTxt, 1)
    }

    private fun finishTransaction() {
        val quantidade = intent.getIntExtra((getString(R.string.coinQuantity)), 0)
        val totalTransaction = intent.getDoubleExtra("successfulTransaction", 0.0)
        cambioMoedas = intent.getSerializableExtra(getString(R.string.currencyData)) as? ModelCurrency
        cambioMoedas.let { moeda ->
            congratulationMessage.let {
                it.append(successfullyTransactionMessageTxt.text)
                    .append("Parabéns \n Você acabou de $buyOrSell\n")
                    .append("$quantidade ${moeda?.isoMoeda} - ${moeda?.nameCurrency},\n")
                    .append("totalizando \n")
                    .append(br.format(totalTransaction))
                    .toString()
                successfullyTransactionMessageTxt.text = it
            }
            buttonBackToHome(moeda)
        }
    }

    private fun buttonBackToHome(cambioMoedas: ModelCurrency?) {
        homeScreenBtn.contentDescription = "Retorno à Home Screen"
        homeScreenBtn.setOnClickListener {
            Intent(this, HomeScreen::class.java).let {
                it.putExtra(getString(R.string.coinValueOnReturn), cambioMoedas)
                finish()
                startActivity(it)
            }
        }
    }
}