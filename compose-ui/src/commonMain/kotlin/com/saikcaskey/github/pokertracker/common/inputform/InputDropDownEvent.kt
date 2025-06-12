package com.saikcaskey.github.pokertracker.common.inputform

import androidx.compose.runtime.Composable
import com.saikcaskey.github.pokertracker.shared.domain.models.Event

@Composable
fun InputDropdownEvent(
    events: List<Event>,
    selectedEvent: Event?,
    onEventSelected: (Event) -> Unit,
    onAddEventClicked: () -> Unit,
) {
    InputSearchableDropdownField(
        label = "Event",
        items = events,
        selectedItem = selectedEvent,
        onItemSelected = onEventSelected,
        filterItems = false,
        itemToString = { it.name ?: it.id.toString() },
        onAddNewItemClicked = onAddEventClicked
    )
}