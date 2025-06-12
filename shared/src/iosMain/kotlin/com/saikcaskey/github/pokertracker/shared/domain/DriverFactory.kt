package com.saikcaskey.github.pokertracker.shared.domain

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.saikcaskey.github.pokertracker.shared.database.PokerTrackerDatabase

actual class DriverFactory {
  actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(PokerTrackerDatabase.Schema, "test.db")
  }
}