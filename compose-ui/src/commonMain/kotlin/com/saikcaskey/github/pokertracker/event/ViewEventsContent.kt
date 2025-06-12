package com.saikcaskey.github.pokertracker.event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saikcaskey.github.pokertracker.common.appbar.TopAppBarItemViewer
import com.saikcaskey.github.pokertracker.common.inputform.InputDropdownSimple
import com.saikcaskey.github.pokertracker.shared.presentation.event.ViewEventsComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEventsContent(component: ViewEventsComponent) {
    val uiState by component.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarItemViewer(
                "All Events",
                onBackClicked = component::onBackClicked,
                onShowInsertItemClicked = component::onShowInsertEventClicked,
                onDeleteAllItemsClicked = component::onDeleteAllEventsClicked,
            )
        },
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(scaffoldPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchFilter.query.orEmpty(),
                onValueChange = component::onSearchQueryChanged,
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            InputDropdownSimple(
                selected = uiState.searchFilter.sort,
                entries = ViewEventsComponent.EventSortOption.entries,
                onSelected = component::onFilterOptionChanged
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(uiState.filtered) { event ->
                    ListItem(
                        headlineContent = { Text(event.name ?: event.id.toString()) },
                        supportingContent = { Text(event.createdAt?.toString().orEmpty()) },
                        modifier = Modifier.clickable { component.onShowEventDetailClicked(event.id) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
