package com.saikcaskey.github.pokertracker.shared.di

import com.saikcaskey.github.pokertracker.shared.domain.DriverFactory
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(driverFactory: DriverFactory): KoinApplication {
    return startKoin {
        modules(appModule(driverFactory))
    }
}
