@file:OptIn(ExperimentalMaterial3Api::class)

package com.saikcaskey.github.pokertracker.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.saikcaskey.github.pokertracker.common.appbar.TopBarItemDetail
import com.saikcaskey.github.pokertracker.common.profitsummary.AnimatedExpenseText
import com.saikcaskey.github.pokertracker.common.section.SectionContainer
import com.saikcaskey.github.pokertracker.shared.data.utils.toUiDateTimeOrNull
import com.saikcaskey.github.pokertracker.shared.domain.models.*
import com.saikcaskey.github.pokertracker.shared.presentation.expense.ExpenseDetailComponent

@Composable
internal fun ExpenseDetailContent(component: ExpenseDetailComponent, modifier: Modifier = Modifier) {
    val state by component.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarItemDetail(
                title = "Expense Detail",
                onDeleteClicked = component::onDeleteExpenseClicked,
                onBackClicked = component::onBackClicked,
                onEditClicked = component::onShowEditExpenseClicked
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            // Expense Detail
            state.expense?.let { expense -> ExpenseDetailSummary(expense) }
            // Venue Detail
            ExpenseVenueSummary(state, onVenueClicked = { venueId -> component.onShowVenueDetailClicked(venueId) })
            // Event Detail
            ExpenseEventSummary(state, onEventClicked = { eventId -> component.onShowEventDetailClicked(eventId) })
        }
    }
}

@Composable
fun ExpenseDetailSummary(expense: Expense) {
    SectionContainer("Info about this Expense") {
        val eventDescription = expense.description
        AnimatedExpenseText(expense, MaterialTheme.typography.bodyLarge)
        Text(expense.prettyName)
        if (!eventDescription.isNullOrBlank()) Text(eventDescription)
        Text("At: ${expense.date?.toUiDateTimeOrNull()}")
        Text("Created:  ${expense.createdAt?.toUiDateTimeOrNull()}")
        Text("Updated At: ${expense.updatedAt?.toUiDateTimeOrNull() ?: "Never"}")
    }
}

@Composable
fun ExpenseVenueSummary(state: ExpenseDetailComponent.UiState, onVenueClicked: (Long) -> Unit) {
    val eventVenue = state.venue
    if (eventVenue != null) {
        SectionContainer(
            title = "Info about the Venue",
            modifier = Modifier.wrapContentHeight().clickable(true) { onVenueClicked(eventVenue.id) },
            content = {
                if (eventVenue.name.isNotBlank()) Text(state.venue?.name.orEmpty())
                Text("Address: ${eventVenue.address}")
                if (!eventVenue.description.isNullOrBlank()) Text(eventVenue.description.orEmpty())
                Text("Created:  ${eventVenue.createdAt?.toUiDateTimeOrNull()}")
                Text("Updated At: ${eventVenue.updatedAt?.toUiDateTimeOrNull()}")
            },
        )
    }
}

@Composable
fun ExpenseEventSummary(state: ExpenseDetailComponent.UiState, onEventClicked: (Long) -> Unit) {
    val event = state.event
    if (event != null) {
        SectionContainer(
            title = "Info about the Event",
            modifier = Modifier.wrapContentHeight().clickable(true) { onEventClicked(event.id) },
            content = {
                val eventName = event.name
                val eventDescription = event.description
                Text(event.gameType.name)
                if (eventName.orEmpty().isNotBlank()) Text(eventName.orEmpty())
                if (eventDescription.isNullOrBlank()) Text(eventDescription.orEmpty())
            },
        )
    }
}
