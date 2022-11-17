package com.example.moedas.userScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.buyOrSell
import java.lang.StringBuilder
import java.math.RoundingMode


class BuyAndSellScreen : AppCompatActivity() {

    private lateinit var txtV_congratulations: TextView
    private lateinit var btn_Home_Screen: Button
    private lateinit var btn_Cambio_Screen: Button
    private var cambioMoedas: ModelCurrency? = null
    private val congratulationMessage = StringBuilder()
    private lateinit var txtToolbar: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_and_sell_screen)

        linkID()
        toolbar()
        finishTransaction()

    }

    private fun toolbar(){
        txtToolbar = findViewById(R.id.txt_toolbar_BuyAndSell)
        val toolbarStatus = intent.getBooleanExtra("Compra", true)
        if (toolbarStatus){
            txtToolbar.text = "Comprar"
        } else{
        txtToolbar.text = "Vender"
        }
    }

    private fun linkID() {
        txtV_congratulations = findViewById(R.id.txT_Congratulation)
        btn_Home_Screen = findViewById(R.id.btn_back_home_screen)
        btn_Cambio_Screen = findViewById(R.id.btn_back_Buy_And_Sell_Screen)
    }

    private fun finishTransaction() {
        val quantidade = intent.getIntExtra("value", 0)
        val totalTransaction = intent.getDoubleExtra("successfulTransaction", 0.0)
        cambioMoedas = intent.getSerializableExtra("coin") as? ModelCurrency
        cambioMoedas.let { moeda ->
            congratulationMessage.let {
                it.append(txtV_congratulations.text)
                    .append("Parabéns \n Você acabou de $buyOrSell\n")
                    .append("$quantidade ${moeda?.isoMoeda} - ${moeda?.nameCurrency},\n")
                    .append("totalizando \n")
                    .append("R$ ${totalTransaction.toBigDecimal().setScale(2, RoundingMode.UP)}")
                    .toString()
                txtV_congratulations.text = it
            }
            buttonBackToHome(moeda)
            buttonBackToCambio()
        }
    }

    private fun buttonBackToHome(cambioMoedas: ModelCurrency?){
        btn_Home_Screen.contentDescription = "Retorno à Home Screen"
        btn_Home_Screen.setOnClickListener {
            Intent(this, HomeScreen::class.java).let {
                it.putExtra("valueCoin", cambioMoedas)
                finish()
                startActivity(it)
            }
        }
    }

    private fun buttonBackToCambio(){
        btn_Cambio_Screen.contentDescription = "Retorno à Cambio Screen"
        btn_Cambio_Screen.setOnClickListener {
            finish()
        }
    }
}