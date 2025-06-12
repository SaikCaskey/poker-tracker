package com.saikcaskey.github.pokertracker.shared.presentation.event

import com.saikcaskey.github.pokertracker.shared.domain.models.GameType
import com.saikcaskey.github.pokertracker.shared.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface InsertEventComponent {
    val uiState: StateFlow<UiState>
    fun onNameChanged(name: String)
    fun onDescriptionChanged(description: String)
    fun onDateChanged(date: LocalDate?)
    fun onTimeChanged(time: LocalTime?)
    fun onGameTypeChanged(type: GameType)
    fun onSubmitClicked()
    fun onBackClicked()
    fun onVenueChanged(venue: Venue)
    fun onShowInsertVenueClicked()

    data class InputData(
        val name: String = "",
        val type: GameType = GameType.CASH,
        val description: String = "",
        val date: LocalDate? = null,
        val time: LocalTime? = null,
    )

    data class UiState(
        val existingEventId: Long? = null,
        val inputData: InputData = InputData(),
        val venues: List<Venue> = emptyList(),
        val venue: Venue? = null,
        val isSubmitEnabled: Boolean = false,
    )
}
