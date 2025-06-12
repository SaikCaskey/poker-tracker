package com.saikcaskey.github.pokertracker.shared.di

import com.saikcaskey.github.pokertracker.shared.data.repository.*
import com.saikcaskey.github.pokertracker.shared.domain.repository.*
import org.koin.core.component.*
import org.koin.dsl.module

val repositoryModule = module {
    single<EventRepository> {
        EventRepositoryImpl(get(), get())
    }
    single<ExpenseRepository> {
        ExpenseRepositoryImpl(get(), get())
    }
    single<VenueRepository> {
        VenueRepositoryImpl(get(), get())
    }
}

object EventRepositoryProvider : KoinComponent {
    fun provide(): EventRepository = get()
}

object ExpenseRepositoryProvider : KoinComponent {
    fun provide(): ExpenseRepository = get()
}

object VenueRepositoryProvider : KoinComponent {
    fun provide(): VenueRepository = get()
}