package com.saikcaskey.github.pokertracker.shared.presentation.expense

import com.saikcaskey.github.pokertracker.shared.domain.models.Event
import com.saikcaskey.github.pokertracker.shared.domain.models.Expense
import com.saikcaskey.github.pokertracker.shared.domain.models.Venue
import kotlinx.coroutines.flow.StateFlow

interface ExpenseDetailComponent {
    val uiState: StateFlow<UiState>

    fun onBackClicked()
    fun onShowVenueDetailClicked(venueId: Long)
    fun onShowEventDetailClicked(eventId: Long)
    fun onShowEditExpenseClicked()
    fun onDeleteExpenseClicked()

    data class UiState(
        val expenseId: Long? = null,
        val expense: Expense? = null,
        val event: Event? = null,
        val venue: Venue? = null,
    )
}

