package com.saikcaskey.github.pokertracker.shared.domain

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.saikcaskey.github.pokertracker.shared.database.PokerTrackerDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            PokerTrackerDatabase.Schema,
            context,
            "test.db",
            callback = object : AndroidSqliteDriver.Callback(PokerTrackerDatabase.Schema) {
                override fun onConfigure(db: SupportSQLiteDatabase) {
                    super.onConfigure(db)
                    db.execSQL("PRAGMA foreign_keys = ON;")
                }
            }
        )
    }
}