package com.example.moedas.userScreens

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.example.moedas.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)


    }

    protected open fun configMenuToolbar(
        toolbar: androidx.appcompat.widget.Toolbar,
        titleText: TextView,
        titleToolbar: String,
        backToolbarButton: Button
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        titleText.text = titleToolbar
        setIsHeading(titleText)
        if(titleToolbar == "Moedas"){
            View.GONE.also { backToolbarButton.visibility = it }
        }else{
            backToolbarButton.visibility = View.VISIBLE
            backToolbarButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun setIsHeading(textView: TextView) {
        ViewCompat.setAccessibilityDelegate(textView, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    host.isAccessibilityHeading = true
                } else {
                    info.isHeading = true
                }
            }
        })
    }

}