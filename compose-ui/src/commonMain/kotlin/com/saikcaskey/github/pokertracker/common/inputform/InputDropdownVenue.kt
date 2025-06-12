package com.saikcaskey.github.pokertracker.common.inputform

import androidx.compose.runtime.Composable
import com.saikcaskey.github.pokertracker.shared.domain.models.Venue

@Composable
fun InputDropdownVenue(
    venues: List<Venue>,
    selectedVenue: Venue?,
    onVenueSelected: (Venue) -> Unit,
    onAddVenueClicked: () -> Unit,
) {
    InputSearchableDropdownField(
        label = "Venue",
        items = venues,
        selectedItem = selectedVenue,
        onItemSelected = onVenueSelected,
        itemToString = { it.name },
        filterItems = false,
        onAddNewItemClicked = onAddVenueClicked
    )
}