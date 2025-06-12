package com.saikcaskey.github.pokertracker.common.profitsummary

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.saikcaskey.github.pokertracker.shared.domain.extensions.formatAsCurrency
import com.saikcaskey.github.pokertracker.shared.domain.models.toProfitColor

@Composable
fun AnimatedProfitText(
    balance: Double,
    forcedColor: Color? = null,
    style: TextStyle = MaterialTheme.typography.displayMedium,
) {
    val animatedBalance by animateFloatAsState(
        targetValue = balance.toFloat(),
        animationSpec = tween(durationMillis = 500),
        label = "BalanceAnimation"
    )

    val animatedColor by animateColorAsState(
        targetValue = balance.toProfitColor(),
        animationSpec = tween(durationMillis = 500),
        label = "ColorAnimation"
    )

    Text(
        text = animatedBalance.toDouble().formatAsCurrency(),
        style = style,
        color = forcedColor ?: animatedColor
    )
}
