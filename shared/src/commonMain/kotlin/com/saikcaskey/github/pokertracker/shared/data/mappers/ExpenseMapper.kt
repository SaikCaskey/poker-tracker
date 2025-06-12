package com.saikcaskey.github.pokertracker.shared.data.mappers

import co.touchlab.kermit.Logger
import com.saikcaskey.github.pokertracker.shared.data.utils.asInstantOrNow
import com.saikcaskey.github.pokertracker.shared.data.utils.asInstantOrNull
import com.saikcaskey.github.pokertracker.shared.database.Expense
import com.saikcaskey.github.pokertracker.shared.domain.models.ExpenseType
import com.saikcaskey.github.pokertracker.shared.domain.models.Expense as DomainExpense

fun Expense.toDomain(): DomainExpense {
    Logger.i("asd expenseToDomain $date")
    return DomainExpense(
        id = id,
        eventId = event_id,
        venueId = venue_id,
        type = ExpenseType.valueOf(type),
        amount = amount,
        description = description,
        date = date.asInstantOrNow(),
        createdAt = created_at.asInstantOrNow(),
        updatedAt = updated_at.asInstantOrNull()
    )
}
