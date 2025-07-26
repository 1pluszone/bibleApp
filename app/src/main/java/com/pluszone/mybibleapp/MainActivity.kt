package com.pluszone.mybibleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pluszone.mybibleapp.data.model.SearchResult
import com.pluszone.mybibleapp.ui.screens.BookListScreen
import com.pluszone.mybibleapp.ui.screens.ChapterListScreen
import com.pluszone.mybibleapp.ui.screens.SearchScreen
import com.pluszone.mybibleapp.ui.screens.VerseListScreen
import com.pluszone.mybibleapp.ui.theme.MyBibleAppTheme
import com.pluszone.mybibleapp.viewmodel.BibleViewModel

sealed class Screen {
    object BookList : Screen()
    object ChapterList : Screen()
    object VerseList : Screen()
    object Search : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel =   BibleViewModel(applicationContext)


        setContent {
            MaterialTheme {
//                var screen by remember { mutableStateOf("books") }
//                var selectedBookIndex by remember { mutableStateOf(0) }
//                var selectedChapterKey by remember { mutableStateOf("1") }

                var screen by remember { mutableStateOf<Screen>(Screen.BookList) }
                var selectedBookIndex by remember { mutableStateOf(0) }
                var selectedChapterKey by remember { mutableStateOf("1") }
                var searchResults by remember { mutableStateOf(listOf<SearchResult>()) }

                when (screen) {
                    Screen.BookList -> BookListScreen(
                        books = viewModel.getBooks(),
                        onBookClick = {
                            selectedBookIndex = it
                            screen = Screen.ChapterList
                        },
                        onSearchClick = {screen = Screen.Search}
                    )

                    Screen.ChapterList -> ChapterListScreen(
                        chapters = viewModel.getChapters(selectedBookIndex),
                        onChapterClick = {
                            selectedChapterKey = it
                            screen = Screen.VerseList
                        }
                    )

                    Screen.VerseList -> VerseListScreen(
                        verses = viewModel.getVerses(selectedBookIndex, selectedChapterKey)
                    )

                    Screen.Search -> SearchScreen(
                        results = searchResults,
                        onBack = { screen = Screen.BookList },
                        onSearch = { query ->
                            searchResults = viewModel.searchVerses(query)
                        }
                    )
//                    "chapters" -> ChapterListScreen(viewModel.getChapters(selectedBookIndex)) {
//                        selectedChapterKey = it
//                        screen = "verses"
//                    }

//                    "verses" -> VerseListScreen(
//                        viewModel.getVerses(
//                            selectedBookIndex,
//                            selectedChapterKey
//                        )
//                    )
                }
            }
        }
    }
}