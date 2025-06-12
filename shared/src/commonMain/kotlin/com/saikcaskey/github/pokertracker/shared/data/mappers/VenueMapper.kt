package com.saikcaskey.github.pokertracker.shared.data.mappers

import com.saikcaskey.github.pokertracker.shared.data.utils.asInstantOrNow
import com.saikcaskey.github.pokertracker.shared.data.utils.asInstantOrNull
import com.saikcaskey.github.pokertracker.shared.database.Venue
import com.saikcaskey.github.pokertracker.shared.domain.models.Venue as DomainVenue

fun Venue.toDomain() = DomainVenue(
    id = id,
    name = name,
    address = address,
    description = description,
    createdAt = created_at.asInstantOrNow(),
    updatedAt = updated_at.asInstantOrNull()
)
