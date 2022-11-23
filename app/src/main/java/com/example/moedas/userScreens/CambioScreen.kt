package com.example.moedas.userScreens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
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
import com.example.moedas.viewModel.ViewModelFactory
import java.io.Serializable
import java.math.RoundingMode



class CambioScreen : AppCompatActivity() {
    private var cambioCurrencyModel: ModelCurrency? = null
    private lateinit var coinListViewModel: ViewModelCurrency
    private lateinit var currencyTxv: TextView
    private lateinit var priceVariationTxt: TextView
    private lateinit var currencyPurchaseValueTxt: TextView
    private lateinit var currencySaleValueTxt: TextView
    private lateinit var coinInCashAvailableTxv: TextView
    private lateinit var balanceAvailableInCashTxv: TextView
    private lateinit var amountEdt: EditText
    private lateinit var backCambioScreenBtn: Button
    private lateinit var buyCambioScreenBtn: Button
    private lateinit var sellCambioScreenBtn: Button
    private var quantidade: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_screen)

        linkedComponents()
        returnSetOnClickListener()
        startViewModel()
        searchSelectedCurrencie()
        setBuyButton(false)
        setSellButton(false)
    }

    override fun onResume() {
        super.onResume()
        amountEdt.text?.clear()
        updateCurrencie()
        cambioCurrencyModel?.let { informationOnScreen(it) }
        cambioCurrencyModel?.let {
            editButtonConfigs(it)
            searchSelectedCurrencie()
        }
    }

    private fun updateCurrencie() {
        cambioCurrencyModel = intent.getSerializableExtra("coinData") as? ModelCurrency
        cambioCurrencyModel?.let { valueCoin ->
            informationOnScreen(valueCoin)
        }
    }

//    inline fun <reified T: Serializable> Bundle.serializable(key: String): T? = when{
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
//        else -> @Suppress("DEPRECATION")getSerializable(key) as? T
//    }

//    fun <T : Serializable?> getSerializableExtra(activity: Activity, name: String, clazz: Class<T>): T {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//            activity.intent.getSerializableExtra("coinData", clazz)!!
//        else
//            activity.intent.getSerializableExtra("coinData") as T
//    }

    private fun startViewModel() {
        coinListViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(RepositoryCurrencies())
            )[ViewModelCurrency::class.java]
    }

    private fun searchSelectedCurrencie() {
        cambioCurrencyModel = intent.getSerializableExtra("coinData") as? ModelCurrency
        cambioCurrencyModel?.let { coin ->
            informationOnScreen(coin)
            editButtonConfigs(coin)
            configBuyButton(coin)
            configSellButton(coin)
        }
    }

    private fun linkedComponents() {
        currencyTxv = findViewById(R.id.nameCurrency)
        priceVariationTxt = findViewById(R.id.textVariation)
        currencyPurchaseValueTxt = findViewById(R.id.textBuyCurrency)
        currencySaleValueTxt = findViewById(R.id.textSellCurrency)
        coinInCashAvailableTxv = findViewById(R.id.text_ValorEmCaixaCambioScreen)
        balanceAvailableInCashTxv = findViewById(R.id.text_SaldoDisponivelCambioScreen)
        backCambioScreenBtn = findViewById(R.id.btn_back_cambio_screen)
        buyCambioScreenBtn = findViewById(R.id.btn_Buy_Cambio_Screen)
        sellCambioScreenBtn = findViewById(R.id.btn_Sell_Cambio_Screen)
        amountEdt = findViewById(R.id.edT_Quantidade)
    }

    private fun returnSetOnClickListener() {
        backCambioScreenBtn.setOnClickListener {
            finish()
        }
    }

    private fun editButtonConfigs(cambioMoedas: ModelCurrency) {
        amountEdt.doOnTextChanged { text, _, _, _ ->
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
        if (cambioCurrencyModel?.isoValueCurrencie == 0) {
            bindValues(cambioMoedas)
        }
        colorVariation()
        currencyTxv.text = "${cambioMoedas.isoMoeda} - ${cambioMoedas.nameCurrency}"
        priceVariationTxt.text = "${
            cambioMoedas.currencyVariation.toString().toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)
        }%"
        //fazer uma função para deixar o código mais enxuto -> Sem códigos repetidos
        if (cambioMoedas.currencyPurchase == null) {
            currencyPurchaseValueTxt.text = "Compra: R$ 0.00"
        } else {
            currencyPurchaseValueTxt.text = "Compra: R$" +
                    "${
                        cambioMoedas.currencyPurchase.toString().toBigDecimal()
                            .setScale(2, RoundingMode.HALF_UP)
                    }"
        }
        if (cambioMoedas.currencySaleValue == null) {
            currencySaleValueTxt.text = "Venda: R$ 0.00"
        } else {
            currencySaleValueTxt.text = "Venda: R$" + "${
                cambioMoedas.currencySaleValue.toString().toBigDecimal()
                    .setScale(2, RoundingMode.HALF_UP)
            }"
        }
        balanceAvailableInCashTxv.text = "Saldo disponível: R$" +
                "${
                    walletValue.toString().toBigDecimal()
                        .setScale(2, RoundingMode.HALF_UP)
                }"
        coinInCashAvailableTxv.text =
            "${cambioMoedas.isoValueCurrencie} ${cambioMoedas.nameCurrency} em caixa"
    }

    private fun colorVariation() {
        if (cambioCurrencyModel?.currencyVariation!! < 0) {
            priceVariationTxt.setTextColor(Color.RED)
        } else if (cambioCurrencyModel?.currencyVariation!! > 0) {
            priceVariationTxt.setTextColor(Color.GREEN)
        } else {
            priceVariationTxt.setTextColor(Color.WHITE)
        }
    }

    private fun enableBuyButton(cambioMoedas: ModelCurrency, quantidade: Int) {
        if (cambioMoedas.currencyPurchase != null) {
            if (quantidade * cambioMoedas.currencyPurchase <= walletValue) {
                setBuyButton(true)
                buyCambioScreenBtn.contentDescription = "Botão Buy habilitado"
            } else {
                setBuyButton(false)
                buyCambioScreenBtn.contentDescription =
                    "Botão Buy desabilitado devido ao saldo para esta operação"
            }
        }

    }

    private fun enableSellButton(cambioMoedas: ModelCurrency, quantidade: Int) {
        if (cambioMoedas.currencySaleValue != null) {
            if (quantidade <= cambioMoedas.isoValueCurrencie) {
                setSellButton(true)
                sellCambioScreenBtn.contentDescription = "Botão Sell habilitado"
            } else {
                setSellButton(false)
                sellCambioScreenBtn.contentDescription =
                    "Botão Sell desabilitado, moedas insuficientes"
            }
        }
    }

    private fun configBuyButton(cambioMoedas: ModelCurrency) {
        buyCambioScreenBtn.setOnClickListener {
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
        sellCambioScreenBtn.setOnClickListener {
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
        buyCambioScreenBtn.isEnabled = boolean
        if (boolean) {
            buyCambioScreenBtn.alpha = 1F
        } else {
            buyCambioScreenBtn.alpha = 0.5F
        }
    }

    private fun setSellButton(boolean: Boolean) {
        sellCambioScreenBtn.isEnabled = boolean
        if (boolean) {
            sellCambioScreenBtn.alpha = 1F
        } else {
            sellCambioScreenBtn.alpha = 0.5F
        }
    }
}
