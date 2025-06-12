@file:OptIn(ExperimentalCoroutinesApi::class)

package com.saikcaskey.github.pokertracker.shared.presentation.expense

import com.arkivanov.decompose.ComponentContext
import com.saikcaskey.github.pokertracker.shared.domain.CoroutineDispatchers
import com.saikcaskey.github.pokertracker.shared.domain.repository.EventRepository
import com.saikcaskey.github.pokertracker.shared.domain.repository.ExpenseRepository
import com.saikcaskey.github.pokertracker.shared.domain.repository.VenueRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultExpenseDetailComponent(
    private val componentContext: ComponentContext,
    private val expenseRepository: ExpenseRepository,
    private val eventRepository: EventRepository,
    private val venueRepository: VenueRepository,
    private val expenseId: Long,
    private val onShowEditExpense: () -> Unit,
    private val onShowEventDetail: (Long) -> Unit,
    private val onShowVenueDetail: (Long) -> Unit,
    private val onFinished: () -> Unit,
    private val dispatchers: CoroutineDispatchers,
) : ExpenseDetailComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val expenseDetail = expenseRepository.getById(expenseId)
        .stateIn(coroutineScope, Eagerly, null)

    private val expenseVenue = expenseDetail.flatMapLatest { expense ->
        expense?.venueId?.let { venueRepository.getById(it) } ?: emptyFlow()
    }.stateIn(coroutineScope, Eagerly, null)

    private val expenseEvent = expenseDetail.flatMapLatest { expense ->
        (expense?.eventId?.let { eventRepository.getById(it) } ?: emptyFlow())
    }.stateIn(coroutineScope, Eagerly, null)

    override val uiState: StateFlow<ExpenseDetailComponent.UiState>
        get() = combine(expenseDetail, expenseVenue, expenseEvent) { expense, venue, event ->
            ExpenseDetailComponent.UiState(expenseId, expense, event, venue)
        }.stateIn(coroutineScope, Eagerly, ExpenseDetailComponent.UiState())


    override fun onBackClicked() = onFinished()
    override fun onShowVenueDetailClicked(venueId: Long) = onShowVenueDetail(venueId)
    override fun onShowEventDetailClicked(eventId: Long) = onShowEventDetail(eventId)
    override fun onShowEditExpenseClicked() = onShowEditExpense()
    override fun onDeleteExpenseClicked() {
        coroutineScope.launch {
            runCatching { expenseRepository.deleteById(expenseId) }
                .onSuccess { withContext(dispatchers.main) { onBackClicked() } }
                .onFailure(Throwable::printStackTrace)
        }
    }
}
