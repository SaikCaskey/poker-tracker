package com.saikcaskey.github.pokertracker.shared.data.repository

import app.cash.sqldelight.coroutines.*
import com.saikcaskey.github.pokertracker.shared.data.mappers.toDomain
import com.saikcaskey.github.pokertracker.shared.database.PokerTrackerDatabase
import com.saikcaskey.github.pokertracker.shared.domain.CoroutineDispatchers
import com.saikcaskey.github.pokertracker.shared.domain.models.Venue
import com.saikcaskey.github.pokertracker.shared.domain.repository.VenueRepository
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import com.saikcaskey.github.pokertracker.shared.database.Venue as DatabaseVenue

class VenueRepositoryImpl(
    private val database: PokerTrackerDatabase,
    private val dispatchers: CoroutineDispatchers,
) : VenueRepository {

    override fun getAll(): Flow<List<Venue>> = database.venueQueries.getAll()
        .asFlow()
        .mapToList(dispatchers.io)
        .map { list -> list.map { it.toDomain() } }

    override fun getRecent(): Flow<List<Venue>> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val today = now.date.toString()

        return database.venueQueries.getRecent(
            beforeDate = today,
            limit = 5
        )
            .asFlow()
            .mapToList(dispatchers.io)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getById(venueId: Long): Flow<Venue> = database.venueQueries.getById(venueId)
        .asFlow()
        .mapToOneNotNull(dispatchers.io)
        .map(DatabaseVenue::toDomain)

    override suspend fun insert(name: String, address: String, description: String) {
        database.venueQueries.insert(
            name = name,
            address = address,
            description = description,
            created_at = Clock.System.now().toString()
        )
    }

    override suspend fun update(
        venueId: Long,
        name: String,
        address: String,
        description: String,
    ) {
        database.venueQueries.update(
            id = venueId,
            name = name,
            address = address,
            description = description,
            updated_at = Clock.System.now().toString(),
        )
    }

    override suspend fun deleteById(venueId: Long) {
        database.venueQueries.deleteById(venueId)
    }

    override suspend fun deleteAll() {
        database.venueQueries.deleteAll()
    }
}
