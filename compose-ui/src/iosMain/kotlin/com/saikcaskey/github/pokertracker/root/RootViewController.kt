package com.saikcaskey.github.pokertracker.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.saikcaskey.github.pokertracker.shared.presentation.root.RootComponent
import platform.UIKit.UIViewController

fun rootViewController(root: RootComponent): UIViewController =
    ComposeUIViewController(configure = { enforceStrictPlistSanityCheck = false }) {
        RootContent(component = root, modifier = Modifier.fillMaxSize())
    }
