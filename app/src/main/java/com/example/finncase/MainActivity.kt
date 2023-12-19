package com.example.finncase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ads.AdsScreen
import com.example.style.theme.FinnCaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinnCaseTheme {
                AdsScreen()
            }
        }
    }
}