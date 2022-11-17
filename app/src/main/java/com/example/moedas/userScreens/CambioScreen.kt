package com.example.moedas.userScreens

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.repository.RepositoryCurrencies
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.afterLinkingValues
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.bindValues
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.buyOrSell
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.walletValue
import com.example.moedas.viewModel.ViewModelCurrency
import com.example.moedas.viewModel.viewModelFactory
import java.math.RoundingMode

class CambioScreen : AppCompatActivity() {

    private var cambioMoeda: ModelCurrency? = null
    private lateinit var coinListViewModel: ViewModelCurrency
    private lateinit var currencyTxV: TextView
    private lateinit var variacaoTxt: TextView
    private lateinit var compraTxt: TextView
    private lateinit var vendaTxt: TextView
    private lateinit var coinInCashAvailableTxV: TextView
    private lateinit var balanceAvailableInCashTxV: TextView
    private lateinit var amountedT: EditText
    private lateinit var btn_Back_Cambio_Screen: Button
    private lateinit var btn_Buy_Cambio_Screen: Button
    private lateinit var btn_Sell_Cambio_Screen: Button
    private var quantidade: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_screen)

        linkID()
        returnSetOnClickListener()
        startViewModel()
        searchSelectedCurrencie()
        setBuyButton(false)
        setSellButton(false)
    }

    override fun onResume() {
        super.onResume()
        amountedT.text?.clear()
        updateCurrencie()
        cambioMoeda?.let { informationOnScreen(it) }
        cambioMoeda?.let { setOnChangedListener(it)
        searchSelectedCurrencie()
        }
    }

    private fun updateCurrencie() {
        cambioMoeda = intent.getSerializableExtra("coinData") as? ModelCurrency
        cambioMoeda?.let { valueCoin ->
            informationOnScreen(valueCoin)
        }
    }

    private fun startViewModel() {
        coinListViewModel =
            ViewModelProvider(
                this,
                viewModelFactory(RepositoryCurrencies())
            )[ViewModelCurrency::class.java]
    }

    private fun searchSelectedCurrencie() {
        cambioMoeda = intent.getSerializableExtra("coinData") as? ModelCurrency
        cambioMoeda.let { coin ->
            if (coin != null) {
                informationOnScreen(coin)
                setOnChangedListener(coin)
                configBuyButton(coin)
                configSellButton(coin)
            }
        }
    }

    private fun linkID() {
        currencyTxV = findViewById(R.id.nameCurrency)
        variacaoTxt = findViewById(R.id.textVariation)
        compraTxt = findViewById(R.id.textBuyCurrency)
        vendaTxt = findViewById(R.id.textSellCurrency)
        coinInCashAvailableTxV = findViewById(R.id.text_ValorEmCaixaCambioScreen)
        balanceAvailableInCashTxV = findViewById(R.id.text_SaldoDisponivelCambioScreen)
        btn_Back_Cambio_Screen = findViewById(R.id.btn_back_cambio_screen)
        btn_Buy_Cambio_Screen = findViewById(R.id.btn_Buy_Cambio_Screen)
        btn_Sell_Cambio_Screen = findViewById(R.id.btn_Sell_Cambio_Screen)
        amountedT = findViewById(R.id.edT_Quantidade)
    }

    private fun returnSetOnClickListener() {
        btn_Back_Cambio_Screen.setOnClickListener {
            finish()
        }
    }

    private fun setOnChangedListener(cambioMoedas: ModelCurrency) {

        amountedT.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank()) {
                quantidade = text.toString().toInt()
                if (quantidade > 0) {
                    enableBuyButton(cambioMoedas, quantidade)
                    enableSellButton(cambioMoedas, quantidade)
                }
            } else {
                setBuyButton(false)
                setSellButton(false)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun informationOnScreen(cambioMoedas: ModelCurrency) {
        if (cambioMoeda?.isoValueCurrencie == 0) {
            bindValues(cambioMoedas)
        }
        coloV()
        currencyTxV.text = "${cambioMoedas.isoMoeda} - ${cambioMoedas.nameCurrency}"
        variacaoTxt.text = "${
            cambioMoedas.currencyVariation.toString().toBigDecimal().setScale(2, RoundingMode.UP)
        }%"
        if (cambioMoedas.currencyPurchase == null) {
            compraTxt.text = "Compra: R$ 0.00"
        } else {
            compraTxt.text = "Compra: R$" +
                    "${
                        cambioMoedas.currencyPurchase.toString().toBigDecimal()
                            .setScale(2, RoundingMode.UP)
                    }"
        }
        if (cambioMoedas.currencySaleValue == null) {
            vendaTxt.text = "Venda: R$ 0.00"
        } else {
            vendaTxt.text = "Venda: R$" + "${
                cambioMoedas.currencySaleValue.toString().toBigDecimal()
                    .setScale(2, RoundingMode.UP)
            }"
        }
        balanceAvailableInCashTxV.text = "Saldo disponível: R$" +
                "${
                    walletValue.toString().toBigDecimal()
                        .setScale(2, RoundingMode.UP)
                }"
        coinInCashAvailableTxV.text =
            "${cambioMoedas.isoValueCurrencie} ${cambioMoedas.nameCurrency} em caixa"
    }

    private fun coloV() {
        if (cambioMoeda?.currencyVariation!! < 0) {
            variacaoTxt.setTextColor(Color.RED)
        } else if (cambioMoeda?.currencyVariation!! > 0) {
            variacaoTxt.setTextColor(Color.GREEN)
        } else {
            variacaoTxt.setTextColor(Color.WHITE)
        }
    }

    private fun enableBuyButton(cambioMoedas: ModelCurrency, quantidade: Int) {
        if (cambioMoedas.currencyPurchase != null) {
            if (quantidade * cambioMoedas.currencyPurchase <= walletValue) {
                setBuyButton(true)
                btn_Buy_Cambio_Screen.contentDescription = "Botão Buy habilitado"
            } else {
                setBuyButton(false)
                btn_Buy_Cambio_Screen.contentDescription =
                    "Botão Buy desabilitado devido ao saldo para esta operação"
            }
        }

    }

    private fun enableSellButton(cambioMoedas: ModelCurrency, quantidade: Int) {
        if (cambioMoedas.currencySaleValue != null) {
            if (quantidade <= cambioMoedas.isoValueCurrencie) {
                setSellButton(true)
                btn_Sell_Cambio_Screen.contentDescription = "Botão Sell habilitado"
            } else {
                setSellButton(false)
                btn_Sell_Cambio_Screen.contentDescription =
                    "Botão Sell desabilitado, moedas insuficientes"
            }
        }
    }

    private fun configBuyButton(cambioMoedas: ModelCurrency) {
        btn_Buy_Cambio_Screen.setOnClickListener {
            cambioMoedas.isoValueCurrencie += quantidade
            afterLinkingValues(cambioMoedas)
            val totalPurchaseAmount = quantidade * cambioMoedas.currencyPurchase!!
            walletValue -= totalPurchaseAmount
            Intent(this, BuyAndSellScreen::class.java).let {
                it.putExtra("Compra", true)
                buyOrSell = "comprar"
                it.putExtra("coin", cambioMoedas)
                it.putExtra("value", quantidade)
                it.putExtra("successfulTransaction", totalPurchaseAmount)
                startActivity(it)
            }
        }
    }

    private fun configSellButton(cambioMoedas: ModelCurrency) {
        btn_Sell_Cambio_Screen.setOnClickListener {
            cambioMoedas.isoValueCurrencie -= quantidade
            afterLinkingValues(cambioMoedas)
            val totalSalesAmount = quantidade * cambioMoedas.currencySaleValue!!
            walletValue += totalSalesAmount
            Intent(this, BuyAndSellScreen::class.java).let {
                it.putExtra("Compra", false)
                buyOrSell = "vender"
                it.putExtra("coin", cambioMoedas)
                it.putExtra("value", quantidade)
                it.putExtra("successfulTransaction", totalSalesAmount)
                startActivity(it)
            }
        }
    }

    private fun setBuyButton(boolean: Boolean) {
        btn_Buy_Cambio_Screen.isEnabled = boolean
        if (boolean) {
            btn_Buy_Cambio_Screen.alpha = 1F
        } else {
            btn_Buy_Cambio_Screen.alpha = 0.5F
        }
    }

    private fun setSellButton(boolean: Boolean) {
        btn_Sell_Cambio_Screen.isEnabled = boolean
        if (boolean) {
            btn_Sell_Cambio_Screen.alpha = 1F
        } else {
            btn_Sell_Cambio_Screen.alpha = 0.5F
        }
    }
}
