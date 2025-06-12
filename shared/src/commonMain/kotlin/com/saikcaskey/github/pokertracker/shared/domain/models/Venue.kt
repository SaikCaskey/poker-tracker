package com.saikcaskey.github.pokertracker.shared.domain.models

import kotlinx.datetime.Instant

data class Venue(
    val id: Long,
    val name: String,
    val address: String?,
    val description: String?,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)
