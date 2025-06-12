package com.saikcaskey.github.pokertracker.shared.di

import com.saikcaskey.github.pokertracker.shared.domain.DriverFactory
import org.koin.core.module.Module

fun appModule(driverFactory: DriverFactory): List<Module> = listOf(
    sharedModule(driverFactory),
    databaseModule,
    repositoryModule,
    dispatchersProvidersModule
)
