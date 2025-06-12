package com.saikcaskey.github.pokertracker.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.saikcaskey.github.pokertracker.root.RootContent
import com.saikcaskey.github.pokertracker.shared.di.*
import com.saikcaskey.github.pokertracker.shared.presentation.root.DefaultRootComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            dispatchers = CoroutineDispatchersProvider.provide(),
            eventRepository = EventRepositoryProvider.provide(),
            venueRepository = VenueRepositoryProvider.provide(),
            expenseRepository = ExpenseRepositoryProvider.provide(),
        )

        setContent {
            RootContent(component = root)
        }
    }
}
