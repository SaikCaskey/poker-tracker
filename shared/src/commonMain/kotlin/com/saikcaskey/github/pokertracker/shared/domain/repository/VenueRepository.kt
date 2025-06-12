package com.saikcaskey.github.pokertracker.shared.domain.repository

import com.saikcaskey.github.pokertracker.shared.domain.models.Venue
import kotlinx.coroutines.flow.Flow

interface VenueRepository {
    fun getAll(): Flow<List<Venue>>
    fun getRecent(): Flow<List<Venue>>
    fun getById(venueId: Long): Flow<Venue>
    suspend fun insert(name: String, address: String, description: String)
    suspend fun update(venueId: Long, name: String, address: String, description: String)
    suspend fun deleteById(venueId: Long)
    suspend fun deleteAll()
}
