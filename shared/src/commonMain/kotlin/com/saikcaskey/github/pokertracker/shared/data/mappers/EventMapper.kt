package com.saikcaskey.github.pokertracker.shared.data.mappers

import com.saikcaskey.github.pokertracker.shared.data.utils.asInstantOrNow
import com.saikcaskey.github.pokertracker.shared.data.utils.asInstantOrNull
import com.saikcaskey.github.pokertracker.shared.database.Event
import com.saikcaskey.github.pokertracker.shared.domain.models.GameType
import com.saikcaskey.github.pokertracker.shared.domain.models.Event as DomainEvent

fun Event.toDomain(): DomainEvent {
    return DomainEvent(
        id = id,
        venueId = venue_id,
        name = name,
        date = date.asInstantOrNow(),
        gameType = GameType.valueOf(game_type),
        description = description,
        createdAt = created_at.asInstantOrNow(),
        updatedAt = updated_at.asInstantOrNull(),
    )
}
