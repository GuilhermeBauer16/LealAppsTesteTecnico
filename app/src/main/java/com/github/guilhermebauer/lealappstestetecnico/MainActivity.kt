package com.github.guilhermebauer.lealappstestetecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.guilhermebauer.lealappstestetecnico.ui.navigation.MainNavHost
import com.github.guilhermebauer.lealappstestetecnico.ui.theme.LealAppsTesteTecnicoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LealAppsTesteTecnicoTheme {
                MainNavHost()
            }
        }
    }
}


