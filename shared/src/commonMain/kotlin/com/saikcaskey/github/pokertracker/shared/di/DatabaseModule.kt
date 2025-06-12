package com.saikcaskey.github.pokertracker.shared.di

import com.saikcaskey.github.pokertracker.shared.database.PokerTrackerDatabase
import com.saikcaskey.github.pokertracker.shared.domain.createDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val databaseModule = module {
    single<PokerTrackerDatabase> {
        createDatabase(get())
    }
}

object DatabaseProvider : KoinComponent {
    fun provide(): PokerTrackerDatabase = get()
}