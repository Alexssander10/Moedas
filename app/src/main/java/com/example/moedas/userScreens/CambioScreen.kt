package com.example.moedas.userScreens

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.moedas.R
import com.example.moedas.model.ModelCurrency
import com.example.moedas.repository.RepositoryCurrencies
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.buyOrSell
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.takeValueHashMap
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.valueTransfModif
import com.example.moedas.utilityFunctions.SingletonValuesCurrencies.walletValue
import com.example.moedas.viewModel.ViewModelCurrency
import com.example.moedas.viewModel.ViewModelFactory
import java.text.NumberFormat
import java.util.*


class CambioScreen : BaseActivity() {

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
    private var amount: Int = 0
    private val localBrazil = Locale("pt", "BR")
    private val br: NumberFormat = NumberFormat.getCurrencyInstance(localBrazil)
    private val brV: NumberFormat = NumberFormat.getCurrencyInstance(localBrazil)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cambio_screen)

        linkedComponents()
        configMenuToolbar(
            findViewById(R.id.toolbarCambioScreen),
            findViewById(R.id.TitleMenuToolbar),getString(R.string.exchangeToolbarTitle),
            backCambioScreenBtn
        )
        startViewModel()
        searchSelectedCurrency()
        setBuyAndSellDisabledButton(false, buyCambioScreenBtn)
        setBuyAndSellDisabledButton(false, sellCambioScreenBtn)
    }

    override fun onResume() {
        super.onResume()
        amountEdt.text?.clear()
        updateCurrency()
        cambioCurrencyModel?.let { informationOnScreen(it) }
        cambioCurrencyModel?.let {
            editButtonConfigs(it)
            searchSelectedCurrency()
        }
    }

    private fun updateCurrency() {
        cambioCurrencyModel = intent.getSerializableExtra("coinData") as? ModelCurrency
        cambioCurrencyModel?.let { valueCoin ->
            informationOnScreen(valueCoin)
        }
    }

    private fun startViewModel() {
        coinListViewModel = ViewModelProvider(
            this, ViewModelFactory(RepositoryCurrencies())
        )[ViewModelCurrency::class.java]
    }

    private fun searchSelectedCurrency() {
        cambioCurrencyModel = intent.getSerializableExtra(getString(R.string.dadosDaMoeda)) as? ModelCurrency
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
        backCambioScreenBtn = findViewById(R.id.btn_back_toolbars_screens)
        buyCambioScreenBtn = findViewById(R.id.btn_Buy_Cambio_Screen)
        sellCambioScreenBtn = findViewById(R.id.btn_Sell_Cambio_Screen)
        amountEdt = findViewById(R.id.edT_Quantidade)
    }

    private fun editButtonConfigs(cambioMoedas: ModelCurrency) {
        amountEdt.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank()) {
                amount = text.toString().toInt()
                if (amount > 0) {
                    enableBuyButton(cambioMoedas, amount)
                    enableSellButton(cambioMoedas, amount)
                }
            } else {
                setBuyAndSellDisabledButton(false, buyCambioScreenBtn)
                setBuyAndSellDisabledButton(false, sellCambioScreenBtn)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun informationOnScreen(cambioMoedas: ModelCurrency) {

        colorVariation()
        currencyTxv.text = buildString {
            append(cambioMoedas.isoMoeda + " - " + cambioMoedas.nameCurrency)
        }
        priceVariationTxt.text =
            (brV.format(cambioMoedas.currencyVariation).replace("R$", "") + "%")

        currencyPurchaseValueTxt.text = buildString {
            append("Compra: " + br.format(cambioMoedas.currencyPurchase).toString())
        }

        if (cambioMoedas.currencySaleValue == null) {
            currencySaleValueTxt.text = "Venda: R$ 0.00"
        } else {
            currencySaleValueTxt.text = buildString {
                append("Venda: " + br.format(cambioMoedas.currencySaleValue).toString())
            }
        }

        balanceAvailableInCashTxv.text = buildString {
            append("Saldo disponível: " + br.format(walletValue).toString())
        }

        coinInCashAvailableTxv.text = buildString {
            append(takeValueHashMap(cambioMoedas.isoMoeda))
            append(" ")
            append(cambioMoedas.nameCurrency)
            append(" em caixa")
        }
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

    private fun enableBuyButton(cambioMoedas: ModelCurrency, amount: Int) {
        if (cambioMoedas.currencyPurchase != null) {
            if (amount * cambioMoedas.currencyPurchase <= walletValue) {
                setBuyAndSellDisabledButton(true, buyCambioScreenBtn)
                buyCambioScreenBtn.contentDescription = "Botão comprar habilitado"
            } else {
                setBuyAndSellDisabledButton(false, buyCambioScreenBtn)
                buyCambioScreenBtn.contentDescription =
                    "Botão comprar desabilitado devido ao saldo para esta operação"
            }
        }
    }

    private fun enableSellButton(cambioMoedas: ModelCurrency, amount: Int) {
        if (cambioMoedas.currencySaleValue != null) {
            if (amount <= takeValueHashMap(cambioMoedas.isoMoeda)) {
                setBuyAndSellDisabledButton(true, sellCambioScreenBtn)
                sellCambioScreenBtn.contentDescription = "Botão vender habilitado"
            } else {
                setBuyAndSellDisabledButton(false, sellCambioScreenBtn)
                sellCambioScreenBtn.contentDescription =
                    "Botão vender desabilitado, moedas insuficientes"
            }
        }
    }

    private fun configBuyButton(cambioMoedas: ModelCurrency) {
        buyCambioScreenBtn.setOnClickListener {
            valueTransfModif(cambioMoedas.isoMoeda,"Buy", amount)
            val totalPurchaseAmount = amount * cambioMoedas.currencyPurchase!!
            walletValue -= totalPurchaseAmount
            Intent(this, BuyAndSellScreen::class.java).let {
                //Compra = define na toolbar se é compra ou venda
                it.putExtra(getString(R.string.Buy), true)
                //comprar = define se a operação compra ou venda
                buyOrSell = "comprar"
                //coin = passa a moeda
                it.putExtra(getString(R.string.currencyData), cambioMoedas)
                //value = quantidade de compra da moeda
                it.putExtra(getString(R.string.coinQuantity), amount)
                //successfulTransaction = valor da compra/venda total
                it.putExtra("successfulTransaction", totalPurchaseAmount)
                startActivity(it)
            }
        }
    }

    private fun configSellButton(cambioMoedas: ModelCurrency) {
        sellCambioScreenBtn.setOnClickListener {
            valueTransfModif(cambioMoedas.isoMoeda,"Sell", amount)
            val totalSalesAmount = amount * cambioMoedas.currencySaleValue!!
            walletValue += totalSalesAmount
            Intent(this, BuyAndSellScreen::class.java).let {
                it.putExtra(getString(R.string.Buy), false)
                buyOrSell = "vender"
                it.putExtra(getString(R.string.currencyData), cambioMoedas)
                it.putExtra(getString(R.string.coinQuantity), amount)
                it.putExtra("successfulTransaction", totalSalesAmount)
                startActivity(it)
            }
        }
    }

    private fun setBuyAndSellDisabledButton(boolean: Boolean, activateOrDeactivate: Button) {
        activateOrDeactivate.isEnabled = boolean
        if (boolean) {
            activateOrDeactivate.alpha = 1F
        } else {
            activateOrDeactivate.alpha = 0.5F
        }
    }
}
