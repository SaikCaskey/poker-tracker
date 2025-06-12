package com.saikcaskey.github.pokertracker.shared.di

import com.saikcaskey.github.pokertracker.shared.domain.DriverFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val sharedModule = { driverFactory: DriverFactory ->
    module {
        single { driverFactory }
    }
}

object DriverFactoryProvider : KoinComponent {
    fun provide(): DriverFactory = get()
}