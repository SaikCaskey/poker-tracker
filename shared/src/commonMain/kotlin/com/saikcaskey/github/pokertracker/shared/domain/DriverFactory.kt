package com.saikcaskey.github.pokertracker.shared.domain

import app.cash.sqldelight.db.SqlDriver
import com.saikcaskey.github.pokertracker.shared.database.PokerTrackerDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): PokerTrackerDatabase {
    val driver = driverFactory.createDriver()
    return PokerTrackerDatabase(driver)
//        .apply { SampleDataSeederImpl().seedSampleData(this) }
}