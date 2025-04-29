package com.example.smartflow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartFlowApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any application-wide components here
        // For example: dependency injection, logging, crash reporting, etc.
    }
}