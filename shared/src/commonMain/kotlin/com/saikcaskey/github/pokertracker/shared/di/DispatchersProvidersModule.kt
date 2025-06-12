package com.saikcaskey.github.pokertracker.shared.di

import com.saikcaskey.github.pokertracker.shared.data.utils.defaultCoroutineDispatchersProviders
import com.saikcaskey.github.pokertracker.shared.domain.CoroutineDispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

val dispatchersProvidersModule = module {
    single<CoroutineDispatchers> {
        defaultCoroutineDispatchersProviders
    }
}

object CoroutineDispatchersProvider : KoinComponent {
    fun provide(): CoroutineDispatchers = get()
}