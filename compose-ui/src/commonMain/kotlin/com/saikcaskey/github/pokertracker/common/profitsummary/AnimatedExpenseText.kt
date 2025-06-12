package com.saikcaskey.github.pokertracker.common.profitsummary

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import com.saikcaskey.github.pokertracker.shared.domain.extensions.formatAsCurrency
import com.saikcaskey.github.pokertracker.shared.domain.models.*

@Composable
fun AnimatedExpenseText(
    expense: Expense,
    style: TextStyle = MaterialTheme.typography.displayMedium,
) {
    val animatedBalance by animateFloatAsState(
        targetValue = expense.amount.toFloat(),
        animationSpec = tween(durationMillis = 500),
        label = "BalanceAnimation"
    )

    val animatedColor by animateColorAsState(
        targetValue = expense.toExpenseColor(),
        animationSpec = tween(durationMillis = 500),
        label = "ColorAnimation"
    )

    val displayText = if (expense.type != ExpenseType.CASH_OUT && expense.type != ExpenseType.DEAL) {
        (-animatedBalance).toDouble().formatAsCurrency()
    } else {
        animatedBalance.toDouble().formatAsCurrency()
    }

    Text(
        text = displayText,
        style = style,
        color = animatedColor
    )
}
