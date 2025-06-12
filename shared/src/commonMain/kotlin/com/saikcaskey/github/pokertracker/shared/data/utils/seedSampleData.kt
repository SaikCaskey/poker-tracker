package com.saikcaskey.github.pokertracker.shared.data.utils

import com.saikcaskey.github.pokertracker.shared.database.PokerTrackerDatabase
import com.saikcaskey.github.pokertracker.shared.domain.models.ExpenseType
import kotlinx.datetime.*
import kotlin.random.Random

fun interface SampleDataSeeder {
    fun seedSampleData(database: PokerTrackerDatabase)
}

class SampleDataSeederImpl : SampleDataSeeder {

    override fun seedSampleData(database: PokerTrackerDatabase) {
        val now = Clock.System.now()
        val timeOffsets = listOf(
            DatePeriod(days = -14),
            DatePeriod(days = -7),
            DatePeriod(days = -3),
            DatePeriod(days = -2),
            DatePeriod(days = -1),
            DatePeriod(days = 1),
            DatePeriod(days = 5),
            DatePeriod(days = 7),
            DatePeriod(days = 14),
            DatePeriod(months = -1),
            DatePeriod(months = 1),
            DatePeriod(months = -2),
            DatePeriod(months = 2),
            DatePeriod(months = -3),
            DatePeriod(months = 3),
        )

        repeat(5) {
            val offset = timeOffsets.random()
            val baseDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
                .plus(offset)
                .atStartOfDayIn(TimeZone.currentSystemDefault())
                .toLocalDateTime(TimeZone.currentSystemDefault())

            insertVenueWithEventAndExpenses(
                database,
                venueName = venueNames.random(),
                description = venueDescriptions.random(),
                eventName = eventNames.random(),
                eventDescription = eventDescriptions.random(),
                baseDate = baseDate
            )
        }
    }

    private fun insertVenueWithEventAndExpenses(
        database: PokerTrackerDatabase,
        venueName: String,
        description: String,
        eventName: String,
        eventDescription: String,
        baseDate: LocalDateTime,
    ) {
        // Insert venue
        database.venueQueries.insert(venueName, "123 Poker Ave", description, baseDate.toString())
        val venueId = database.venueQueries.lastInsertRowId().executeAsOne()

        // Insert event
        val gameType = listOf("CASH", "TOURNAMENT").random()
        database.eventQueries.insert(
            venue_id = venueId,
            name = eventName,
            date = baseDate.toString(),
            game_type = gameType,
            description = eventDescription,
            created_at = baseDate.toString(),
        )
        val eventId = database.eventQueries.lastInsertRowId().executeAsOne()

        insertSimulatedExpenses(database, eventId, venueId, baseDate)
    }

    private fun insertSimulatedExpenses(
        database: PokerTrackerDatabase,
        eventId: Long,
        venueId: Long,
        baseDate: LocalDateTime,
    ) {
        val date = baseDate.toString()
        val createdAt = Clock.System.now().toString()

        // Fixed buy-in
        val buyInAmount = 200.0
        database.expenseQueries.insert(eventId, venueId, "BUY_IN", buyInAmount, "Buy-in", date, createdAt)

        // Random other expenses
        val extraCount = Random.nextInt(3, 15)
        repeat(extraCount) {
            val type = ExpenseType.valueOf(extraExpenseTypes.random())
            val amount = type.toRandomExpenseAmount()
            val note = expenseDescriptions.random()
            database.expenseQueries.insert(eventId, venueId, type.toString(), amount, note, date, createdAt)
        }

        // Cashout result: profit or bust
        val cashOutAmount = Random.nextDouble(0.00, 1000.0)
        database.expenseQueries.insert(eventId, venueId, "CASH_OUT", cashOutAmount, "Cashout", date, createdAt)

    }

    private fun ExpenseType.toRandomExpenseAmount(): Double {
        return when (this) {
            ExpenseType.ADD_ON -> Random.nextDouble(50.0, 500.0)
            ExpenseType.REBUY -> Random.nextDouble(200.0, 1000.0)
            ExpenseType.DRINKS -> Random.nextDouble(5.0, 1000.0)
            ExpenseType.FINE -> if (Random.nextInt(5) > 4) {
                Random.nextDouble(600.0, 1000.0)
            } else {
                Random.nextDouble(5.0, 20.0)
            }

            else -> {
                Random.nextDouble(10.0, 40.0)
            }
        }
    }

    private val venueNames = listOf(
        "Bellagio",
        "The Mirage",
        "ARIA",
        "Wynn",
        "Caesars Palace",
        "MGM Grand",
        "Red Rock",
        "Golden Nugget"
    )

    private val venueDescriptions = listOf(
        "Famous poker room",
        "Luxury experience",
        "Tight games, good drinks",
        "Great cash tables",
        "Mixed games available",
        "Best high roller room",
        "Cheap drinks and loose games"
    )

    private val eventNames = listOf(
        "Nightly $200 Tournament",
        "Deepstack Saturday",
        "Sunday Shootout",
        "Midweek Madness",
        "High Roller NLHE",
        "Noon Turbo",
        "Rebuy Rumble"
    )

    private val eventDescriptions = listOf(
        "Great structure, slow blinds",
        "Fast-paced action",
        "Lots of regulars",
        "New player friendly",
        "Lots of re-entries",
        "High rake but soft field",
        "Tight aggressive tables"
    )

    private val extraExpenseTypes = listOf(
        "FOOD",
        "DRINKS",
        "TRANSPORT",
        "MISC",
        "ADD_ON"
    )

    private val expenseDescriptions = listOf(
        "Lunch",
        "Taxi fare",
        "Beer",
        "Snacks",
        "Tip to dealer",
        "Parking",
        "Coffee",
        "Late reg fee"
    )
}
