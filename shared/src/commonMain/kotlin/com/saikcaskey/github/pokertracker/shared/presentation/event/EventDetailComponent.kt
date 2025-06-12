package com.saikcaskey.github.pokertracker.shared.presentation.event

import com.saikcaskey.github.pokertracker.shared.domain.models.Event
import com.saikcaskey.github.pokertracker.shared.domain.models.Expense
import com.saikcaskey.github.pokertracker.shared.domain.models.ProfitSummary
import com.saikcaskey.github.pokertracker.shared.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface EventDetailComponent {
    val uiState: StateFlow<UiState>

    fun onBackClicked()
    fun onDeleteEventClicked()
    fun onShowEditEventClicked()
    fun onShowInsertExpenseClicked()
    fun onShowExpenseDetailClicked(expenseId: Long)
    fun onShowVenueDetailClicked(venueId: Long)

    data class UiState(
        val id: Long? = null,
        val event: Event? = null,
        val venue: Venue? = null,
        val expenses: List<Expense> = emptyList(),
        val profitSummary: ProfitSummary = ProfitSummary(),
    )
}
