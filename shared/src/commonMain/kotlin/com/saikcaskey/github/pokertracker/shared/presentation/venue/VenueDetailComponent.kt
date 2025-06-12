package com.saikcaskey.github.pokertracker.shared.presentation.venue

import com.saikcaskey.github.pokertracker.shared.domain.models.Event
import com.saikcaskey.github.pokertracker.shared.domain.models.ProfitSummary
import com.saikcaskey.github.pokertracker.shared.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface VenueDetailComponent {

    val uiState: StateFlow<UiState>
    fun onBackClicked()
    fun onShowInsertEventClicked()
    fun onShowEditVenueClicked()
    fun onDeleteVenueClicked()
    fun onShowEventDetailClicked(eventId: Long)

    data class UiState(
        val id: Long? = null,
        val venue: Venue? = null,
        val pastEvents: List<Event> = emptyList(),
        val upcomingEvents: List<Event> = emptyList(),
        val profitSummary: ProfitSummary = ProfitSummary(),
    )
}
