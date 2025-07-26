package com.pluszone.mybibleapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pluszone.mybibleapp.ui.uiUtil.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterListScreen(
    title: String,
    chapters: List<String>,
    onChapterClick: (String) -> Unit
) {
    Scaffold(
        topBar = {TopBar(title = title)},
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(chapters) { chapter ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onChapterClick(chapter) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "Chapter $chapter",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}