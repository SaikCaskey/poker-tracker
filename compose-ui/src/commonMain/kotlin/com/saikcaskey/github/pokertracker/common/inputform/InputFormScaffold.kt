package com.saikcaskey.github.pokertracker.common.inputform

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saikcaskey.github.pokertracker.common.appbar.TopBarBackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFormScaffold(
    title: String,
    onBackClicked: () -> Unit,
    onSubmit: () -> Unit,
    isSubmitEnabled: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { TopBarBackButton(onBackClicked = onBackClicked) },
                actions = {
                    TextButton(onClick = onSubmit, enabled = isSubmitEnabled) {
                        Text("Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}
