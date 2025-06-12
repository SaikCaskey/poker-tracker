package com.saikcaskey.github.pokertracker.shared.presentation.planner

import com.saikcaskey.github.pokertracker.shared.domain.models.Event
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface DayDetailComponent {
    val uiState: StateFlow<UiState>

    fun onShowEventDetailClicked(eventId: Long)

    fun onShowInsertEventClicked()

    fun onBackClicked()

    data class UiState(
        val date: LocalDate,
        val events: List<Event> = emptyList(),
    )
}