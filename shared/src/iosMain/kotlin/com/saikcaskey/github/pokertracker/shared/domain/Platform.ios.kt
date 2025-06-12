package com.saikcaskey.github.pokertracker.shared.domain

import platform.UIKit.UIDevice

actual fun getPlatformName(): String =
    "${UIDevice.Companion.currentDevice.systemName()} ${UIDevice.Companion.currentDevice.systemVersion}"