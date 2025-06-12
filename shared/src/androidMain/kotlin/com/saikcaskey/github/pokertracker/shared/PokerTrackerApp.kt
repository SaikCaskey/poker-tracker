package com.saikcaskey.github.pokertracker.shared

import android.app.Application
import com.saikcaskey.github.pokertracker.shared.di.initKoin
import com.saikcaskey.github.pokertracker.shared.domain.DriverFactory

class PokerTrackerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(DriverFactory(this))
    }
}