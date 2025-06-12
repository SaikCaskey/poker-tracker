package com.saikcaskey.github.pokertracker.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.*
import com.saikcaskey.github.pokertracker.common.theme.AppTheme
import com.saikcaskey.github.pokertracker.event.*
import com.saikcaskey.github.pokertracker.expense.*
import com.saikcaskey.github.pokertracker.main.MainContent
import com.saikcaskey.github.pokertracker.planner.DayDetailContent
import com.saikcaskey.github.pokertracker.shared.presentation.root.RootComponent
import com.saikcaskey.github.pokertracker.shared.presentation.root.RootComponent.Child
import com.saikcaskey.github.pokertracker.venue.*

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    AppTheme(Color(70, 51, 250)) {
        Surface(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
            Children(
                stack = component.rootNavigationStack,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val instance = it.instance) {
                    is Child.Main -> MainContent(component = instance.component)
                    is Child.ViewExpenses -> ViewExpensesContent(component = instance.component)
                    is Child.ViewEvents -> ViewEventsContent(component = instance.component)
                    is Child.ViewVenues -> ViewVenuesContent(component = instance.component)
                    is Child.EventDetail -> EventDetailContent(component = instance.component)
                    is Child.ExpenseDetail -> ExpenseDetailContent(component = instance.component)
                    is Child.VenueDetail -> VenueDetailContent(component = instance.component)
                    is Child.InsertEvent -> InsertEventContent(component = instance.component)
                    is Child.InsertExpense -> InsertExpenseContent(component = instance.component)
                    is Child.InsertVenue -> InsertVenueContent(component = instance.component)
                    is Child.DayDetail -> DayDetailContent(component = instance.component)
                }
            }
        }
    }
}
