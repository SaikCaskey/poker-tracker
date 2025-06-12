package com.saikcaskey.github.pokertracker.shared.data.utils

import com.saikcaskey.github.pokertracker.shared.domain.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

val defaultCoroutineDispatchersProviders = object : CoroutineDispatchers {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}
