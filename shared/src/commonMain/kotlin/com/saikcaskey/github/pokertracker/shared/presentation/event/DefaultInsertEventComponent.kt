@file:OptIn(ExperimentalCoroutinesApi::class)

package com.saikcaskey.github.pokertracker.shared.presentation.event

import com.arkivanov.decompose.ComponentContext
import com.saikcaskey.github.pokertracker.shared.domain.CoroutineDispatchers
import com.saikcaskey.github.pokertracker.shared.domain.models.*
import com.saikcaskey.github.pokertracker.shared.domain.repository.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.datetime.*

class DefaultInsertEventComponent(
    private val componentContext: ComponentContext,
    existingEventId: Long? = null,
    venueId: Long? = null,
    startDate: LocalDate? = null,
    private val eventRepository: EventRepository,
    venueRepository: VenueRepository,
    private val dispatchers: CoroutineDispatchers,
    private val onShowInsertVenue: () -> Unit,
    private val onFinished: () -> Unit,
) : InsertEventComponent, ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(dispatchers.io)

    private val _selectedVenueId = MutableStateFlow(venueId)

    private val _inputData = MutableStateFlow(InsertEventComponent.InputData(date = startDate,))

    private val _venues: StateFlow<List<Venue>> = venueRepository.getAll()
        .stateIn(coroutineScope, Eagerly, emptyList())

    private val _venue: StateFlow<Venue?> = _selectedVenueId
        .flatMapLatest { venueId ->
            if (venueId == null) {
                emptyFlow()
            } else {
                venueRepository.getById(venueId)
            }
        }
        .stateIn(coroutineScope, Eagerly, null)

    override val uiState: StateFlow<InsertEventComponent.UiState> = combine(
        _inputData,
        _venues,
        _venue,
    ) { inputData, venues, venue ->
        InsertEventComponent.UiState(
            existingEventId = existingEventId,
            inputData = inputData,
            venues = venues,
            venue = venue,
            isSubmitEnabled = inputData.name.isNotBlank(),
        )
    }.stateIn(
        scope = coroutineScope,
        started = Eagerly,
        initialValue = InsertEventComponent.UiState(
            existingEventId = existingEventId,
            inputData = _inputData.value,
            venue = _venue.value,
        )
    )

    init {
        coroutineScope.launch {
            existingEventId?.let {
                eventRepository.getById(existingEventId).collectLatest { event ->
                    _selectedVenueId.value = event?.venueId
                    _inputData.update {
                        InsertEventComponent.InputData(
                            name = event?.name.orEmpty(),
                            date = event?.date?.toLocalDateTime(TimeZone.currentSystemDefault())?.date,
                            time = event?.date?.toLocalDateTime(TimeZone.currentSystemDefault())?.time,
                            type = event?.gameType ?: GameType.CASH,
                            description = event?.description.orEmpty(),
                        )
                    }
                }
            }
        }
    }

    override fun onNameChanged(name: String) {
        _inputData.update { it.copy(name = name) }
    }

    override fun onDateChanged(date: LocalDate?) {
        _inputData.update { it.copy(date = date) }
    }

    override fun onTimeChanged(time: LocalTime?) {
        _inputData.update { it.copy(time = time) }
    }

    override fun onDescriptionChanged(description: String) {
        _inputData.update { it.copy(description = description) }
    }

    override fun onGameTypeChanged(type: GameType) {
        _inputData.update { it.copy(type = type) }
    }

    override fun onVenueChanged(venue: Venue) {
        _selectedVenueId.value = venue.id
    }

    override fun onSubmitClicked() {
        val state = uiState.value
        coroutineScope.launch {
            runCatching {
                val eventId = uiState.value.existingEventId
                if (eventId != null) {
                    eventRepository.update(
                        id = eventId,
                        venueId = state.venue?.id,
                        name = state.inputData.name,
                        date = state.inputData.date?.toString(),
                        time = state.inputData.time?.toString(),
                        gameType = state.inputData.type.name,
                        description = state.inputData.description,
                    )
                } else {
                    eventRepository.insert(
                        venueId = state.venue?.id,
                        name = state.inputData.name,
                        date = state.inputData.date?.toString(),
                        time = state.inputData.time?.toString(),
                        gameType = state.inputData.type.name,
                        description = state.inputData.description,
                    )
                }
            }
                .onSuccess { withContext(dispatchers.main) { onFinished() } }
                .onFailure(Throwable::printStackTrace)
        }
    }

    override fun onShowInsertVenueClicked() {
        onShowInsertVenue()
    }

    override fun onBackClicked() {
        onFinished()
    }
}
