package com.example.moedas

import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
// Tentativa de implementação do teste de acessibilidade
class TestAccessibility {
    companion object {
        @Before @JvmStatic
        fun enableAccessibilityChecks(){
            AccessibilityChecks.enable().apply {
            }
        }
    }

}