@file:OptIn(ExperimentalMaterial3Api::class)

package com.saikcaskey.github.pokertracker.venue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.saikcaskey.github.pokertracker.common.appbar.TopBarItemDetail
import com.saikcaskey.github.pokertracker.common.profitsummary.AnimatedProfitText
import com.saikcaskey.github.pokertracker.common.section.*
import com.saikcaskey.github.pokertracker.main.DashboardEventsList
import com.saikcaskey.github.pokertracker.shared.data.utils.toUiDateTimeOrNull
import com.saikcaskey.github.pokertracker.shared.domain.models.*
import com.saikcaskey.github.pokertracker.shared.presentation.venue.VenueDetailComponent
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.PlusCircle

@Composable
internal fun VenueDetailContent(modifier: Modifier = Modifier, component: VenueDetailComponent) {
    val state by component.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBarItemDetail(
                title = "Venue Detail",
                onDeleteClicked = component::onDeleteVenueClicked,
                onBackClicked = component::onBackClicked,
                onEditClicked = component::onShowEditVenueClicked
            )
        },
        floatingActionButton = {
            FloatingActionButton(component::onShowInsertEventClicked) {
                Icon(
                    modifier = Modifier.height(32.dp),
                    imageVector = FontAwesomeIcons.Solid.PlusCircle,
                    contentDescription = "Add event"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
        ) {
            item { VenueDetailSummary(state) }
            item { VenueProfitSummary(state) }
            item {
                VenueEventsSummary(
                    upcomingEvents = state.upcomingEvents,
                    pastEvents = state.pastEvents,
                    onEventClicked = component::onShowEventDetailClicked
                )
            }
        }
    }
}

@Composable
fun VenueEventsSummary(
    upcomingEvents: List<Event>,
    pastEvents: List<Event>,
    onEventClicked: (Long) -> Unit,
) {
    SectionContainer(
        title = "Events",
    ) {
        Text(text = "Upcoming", style = MaterialTheme.typography.labelLarge)
        DashboardEventsList(
            items = upcomingEvents,
            emptyMessage = "",
            onEventClicked = onEventClicked,
        )
        Spacer(Modifier.height(8.dp))
        Text(text = "Recent", style = MaterialTheme.typography.labelLarge)
        DashboardEventsList(
            items = pastEvents,
            emptyMessage = "",
            onEventClicked = onEventClicked,
        )
    }
}

@Composable
fun VenueDetailSummary(state: VenueDetailComponent.UiState) {
    SectionContainer(modifier = Modifier.fillMaxHeight()) {
        Text(state.venue?.name.orEmpty(), style = MaterialTheme.typography.displaySmall)
        Text("Id: ${state.venue?.id}")
        Text("Created:  ${state.venue?.createdAt?.toUiDateTimeOrNull()}")
        if (state.venue?.description != null) {
            Spacer(Modifier.height(4.dp))
            Text(state.venue?.description.orEmpty())
        }
        if (state.venue?.address != null) {
            Spacer(Modifier.height(4.dp))
            Text(state.venue?.address.orEmpty())
        }
    }
}

@Composable
fun VenueProfitSummary(
    state: VenueDetailComponent.UiState,
    onVenueClicked: ((Long) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    SectionContainer(
        title = "Venue Cashflow",
        modifier = modifier.fillMaxSize().clickable(state.venue?.id != null) {
            state.venue?.id?.let { onVenueClicked?.invoke(it) }
        }
    ) {
        Text("Expenses:")
        AnimatedProfitText(
            state.profitSummary.costsSubtotal,
            forcedColor = (-state.profitSummary.costsSubtotal).toProfitColor(),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 26.sp)
        )
        Text("Cashout:")
        AnimatedProfitText(
            state.profitSummary.cashesSubtotal,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 26.sp)
        )
        Text("Total:", style = MaterialTheme.typography.titleMedium)
        AnimatedProfitText(state.profitSummary.balance)
    }
}
