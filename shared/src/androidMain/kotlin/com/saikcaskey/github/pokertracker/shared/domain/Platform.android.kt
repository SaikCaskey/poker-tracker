package com.saikcaskey.github.pokertracker.shared.domain

import android.os.Build

actual fun getPlatformName(): String = "Android ${Build.VERSION.SDK_INT}"