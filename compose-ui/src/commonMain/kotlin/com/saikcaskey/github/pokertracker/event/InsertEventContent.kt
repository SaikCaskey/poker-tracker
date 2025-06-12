@file:OptIn(ExperimentalMaterial3Api::class)

package com.saikcaskey.github.pokertracker.event

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.saikcaskey.github.pokertracker.common.inputform.*
import com.saikcaskey.github.pokertracker.shared.domain.models.GameType
import com.saikcaskey.github.pokertracker.shared.presentation.event.InsertEventComponent
import kotlinx.datetime.*

@Composable
fun InsertEventContent(component: InsertEventComponent) {

    val state by component.uiState.collectAsState()

    InputFormScaffold(
        title = if (state.existingEventId == null) "Insert Event" else "Edit Event",
        onBackClicked = component::onBackClicked,
        onSubmit = component::onSubmitClicked,
        isSubmitEnabled = state.isSubmitEnabled
    ) {
        InputDropdownField(
            value = state.inputData.type,
            label = "Event Type",
            modifier = Modifier.fillMaxWidth(),
            options = GameType.entries.toList(),
            onSelected = component::onGameTypeChanged,
        )
        OutlinedTextField(
            value = state.inputData.name,
            onValueChange = component::onNameChanged,
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.inputData.description,
            onValueChange = component::onDescriptionChanged,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        InputDropdownVenue(
            venues = state.venues,
            selectedVenue = state.venue,
            onVenueSelected = component::onVenueChanged,
            onAddVenueClicked = component::onShowInsertVenueClicked
        )
        InputDateField(
            value = state.inputData.date,
            onValueChange = component::onDateChanged,
            label = "Date",
            modifier = Modifier.fillMaxWidth(),
        )
        InputTimeField(
            value = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            onValueChange = component::onTimeChanged,
            label = "Time",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
