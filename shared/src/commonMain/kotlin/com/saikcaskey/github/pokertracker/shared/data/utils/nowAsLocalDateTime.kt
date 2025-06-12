package com.saikcaskey.github.pokertracker.shared.data.utils

import kotlinx.datetime.*

fun nowAsLocalDateTime(timeZone: TimeZone? = null): LocalDateTime {
    val timeZone = timeZone ?: TimeZone.currentSystemDefault()
    return Clock.System.now()
        .toLocalDateTime(timeZone)
}