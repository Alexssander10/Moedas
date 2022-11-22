package com.example.moedas.userScreens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.buyOrSell
import java.lang.StringBuilder
import java.math.RoundingMode


class BuyAndSellScreen : AppCompatActivity() {

    private lateinit var successfullyTransactionMessageTxt: TextView
    private lateinit var homeScreenBtn: Button
    private lateinit var cambioScreenBtn: Button
    private var cambioMoedas: ModelCurrency? = null
    private val congratulationMessage = StringBuilder()
    private lateinit var txtToolbar: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_and_sell_screen)

        linkID()
        toolbar()
        finishTransaction()
        textViewConfigs()

    }

    @SuppressLint("SetTextI18n")
    private fun toolbar() {
        txtToolbar = findViewById(R.id.txt_toolbar_BuyAndSell)
        val toolbarStatus = intent.getBooleanExtra("Compra", true)
        if (toolbarStatus) {
            txtToolbar.text = "Comprar"
        } else {
            txtToolbar.text = "Vender"
        }
    }

    private fun linkID() {
        successfullyTransactionMessageTxt = findViewById(R.id.txT_Congratulation)
        homeScreenBtn = findViewById(R.id.btn_back_home_screen)
        cambioScreenBtn = findViewById(R.id.btn_back_Buy_And_Sell_Screen)
    }

    private fun textViewConfigs() {
        TextViewCompat.setAutoSizeTextTypeWithDefaults(successfullyTransactionMessageTxt, 1)
    }

    private fun finishTransaction() {
        val quantidade = intent.getIntExtra("value", 0)
        val totalTransaction = intent.getDoubleExtra("successfulTransaction", 0.0)
        cambioMoedas = intent.getSerializableExtra("coin") as? ModelCurrency
        cambioMoedas.let { moeda ->
            congratulationMessage.let {
                it.append(successfullyTransactionMessageTxt.text)
                    .append("Parabéns \n Você acabou de $buyOrSell\n")
                    .append("$quantidade ${moeda?.isoMoeda} - ${moeda?.nameCurrency},\n")
                    .append("totalizando \n")
                    .append(
                        "R$ ${
                            totalTransaction.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                        }"
                    )
                    .toString()
                successfullyTransactionMessageTxt.text = it
            }
            buttonBackToHome(moeda)
            buttonBackToCambio()
        }
    }

    private fun buttonBackToHome(cambioMoedas: ModelCurrency?) {
        homeScreenBtn.contentDescription = "Retorno à Home Screen"
        homeScreenBtn.setOnClickListener {
            Intent(this, HomeScreen::class.java).let {
                it.putExtra("valueCoin", cambioMoedas)
                finish()
                startActivity(it)
            }
        }
    }

    private fun buttonBackToCambio() {
        cambioScreenBtn.contentDescription = "Retorno à Cambio Screen"
        cambioScreenBtn.setOnClickListener {
            finish()
        }
    }
}