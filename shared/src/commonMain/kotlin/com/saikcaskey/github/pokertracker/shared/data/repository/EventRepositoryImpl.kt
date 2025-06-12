    package com.saikcaskey.github.pokertracker.shared.data.repository

    import app.cash.sqldelight.coroutines.*
    import co.touchlab.kermit.Logger
    import com.saikcaskey.github.pokertracker.shared.data.mappers.toDomain
    import com.saikcaskey.github.pokertracker.shared.database.PokerTrackerDatabase
    import com.saikcaskey.github.pokertracker.shared.domain.CoroutineDispatchers
    import com.saikcaskey.github.pokertracker.shared.domain.models.Event
    import com.saikcaskey.github.pokertracker.shared.domain.repository.EventRepository
    import kotlinx.coroutines.flow.*
    import kotlinx.datetime.*
    import com.saikcaskey.github.pokertracker.shared.database.Event as DatabaseEvent

    class EventRepositoryImpl(
        private val database: PokerTrackerDatabase,
        private val coroutineDispatchers: CoroutineDispatchers,
    ) : EventRepository {

        override fun getAll(): Flow<List<Event>> =
            database.eventQueries.getAll()
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { events -> events.map { event -> event.toDomain() } }

        override fun getDaysWithEvents(): Flow<List<LocalDate>> {
            return database.eventQueries.getEventDates()
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { events -> events.map { LocalDate.parse(it) } }
        }

        override fun getByDate(date: LocalDate): Flow<List<Event>> {
            return database.eventQueries.getByDate(date.toString())
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { events -> events.map(DatabaseEvent::toDomain) }
        }

        override fun getRecent(): Flow<List<Event>> {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val today = now.date.atStartOfDayIn(TimeZone.currentSystemDefault()).toString()

            return database.eventQueries.getRecent(today, 5)
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { events -> events.map { event -> event.toDomain() } }
        }

        override fun getRecentByVenue(venueId: Long?): Flow<List<Event>> {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val tomorrow = now.date.atStartOfDayIn(TimeZone.currentSystemDefault()).toString()

            return database.eventQueries.getRecentByVenue(tomorrow, venueId)
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { events -> events.map { event -> event.toDomain() } }
        }

        override fun getUpcoming(): Flow<List<Event>> {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val tomorrow = now.date.plus(DatePeriod(days = 1))
                .atStartOfDayIn(TimeZone.currentSystemDefault()).toString()

            return database.eventQueries.getUpcoming(tomorrow, 5)
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { events -> events.map { event -> event.toDomain() } }
        }

        override fun getUpcomingByVenue(venueId: Long?): Flow<List<Event>> {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val startOfTomorrow = now.date.plus(DatePeriod(days = 1))
                .atStartOfDayIn(TimeZone.currentSystemDefault()).toString()

            return database.eventQueries.getUpcomingByVenue(startOfTomorrow, venueId)
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { events -> events.map { event -> event.toDomain() } }
        }

        override fun getById(eventId: Long): Flow<Event?> {
            return database.eventQueries.getById(eventId)
                .asFlow()
                .mapToOneOrNull(coroutineDispatchers.io)
                .filterNotNull()
                .map(DatabaseEvent::toDomain)
        }

        override fun getByVenue(venueId: Long): Flow<List<Event>> {
            return database.eventQueries.getByVenue(venueId)
                .asFlow()
                .mapToList(coroutineDispatchers.io)
                .map { venues -> venues.map(DatabaseEvent::toDomain) }
        }

        override suspend fun insert(
            name: String,
            gameType: String,
            venueId: Long?,
            date: String?,
            time: String?,
            description: String?,
        ) {
            database.eventQueries.transaction {
                val parsedLocalDate = LocalDate.parse(date.orEmpty())
                val parsedTime = LocalTime.parse(time.orEmpty())
                database.eventQueries.insert(
                    venue_id = venueId,
                    name = name,
                    date = parsedLocalDate.atTime(parsedTime).toInstant(TimeZone.currentSystemDefault()).toString(),
                    game_type = gameType,
                    description = description,
                    created_at = Clock.System.now().toString(),
                )
            }
        }

        override suspend fun update(
            id: Long,
            name: String,
            gameType: String,
            venueId: Long?,
            date: String?,
            time: String?,
            description: String?,
        ) {
            database.eventQueries.transaction {
                val parsedLocalDate = LocalDate.parse(date.orEmpty())
                val parsedTime = LocalTime.parse(time.orEmpty())
                parsedLocalDate.atTime(parsedTime).toInstant(TimeZone.currentSystemDefault()).toString()
                database.eventQueries.update(
                    id = id,
                    venue_id = venueId,
                    name = name,
                    date = parsedLocalDate.atTime(parsedTime).toInstant(TimeZone.currentSystemDefault()).toString(),
                    game_type = gameType,
                    description = description,
                    updated_at = Clock.System.now().toString()
                )
            }
        }

        override suspend fun deleteById(eventId: Long) {
            database.eventQueries.deleteById(eventId)
        }

        override suspend fun deleteAll() {
            database.eventQueries.deleteAll()
        }
    }
