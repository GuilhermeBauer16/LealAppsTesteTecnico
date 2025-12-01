package com.github.guilhermebauer.lealappstestetecnico

// Crie este novo arquivo: MyApplication.kt

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}