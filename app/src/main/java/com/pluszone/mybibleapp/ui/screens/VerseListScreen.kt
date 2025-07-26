package com.pluszone.mybibleapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
fun VerseListScreen(
    title: String,
    verses: List<Pair<String, String>>,
    highlighted: Set<String> = emptySet(),
    onVerseClick: ((String) -> Unit)? = null
) {
    Scaffold(
        topBar =  {TopBar(title = title)}

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(verses) { (verseNum, verseText) ->
                val isHighlighted = highlighted.contains(verseNum)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onVerseClick?.invoke(verseNum) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isHighlighted) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        text = "$verseNum. $verseText",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}