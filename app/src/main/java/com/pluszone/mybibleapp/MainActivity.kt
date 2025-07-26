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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pluszone.mybibleapp.data.model.SearchResult
import com.pluszone.mybibleapp.ui.Screen
import com.pluszone.mybibleapp.ui.screens.BookListScreen
import com.pluszone.mybibleapp.ui.screens.ChapterListScreen
import com.pluszone.mybibleapp.ui.screens.SearchScreen
import com.pluszone.mybibleapp.ui.screens.VerseListScreen
import com.pluszone.mybibleapp.ui.theme.MyBibleAppTheme
import com.pluszone.mybibleapp.viewmodel.BibleViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel =   BibleViewModel(applicationContext)


        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.BookList.route
                ) {
                    composable(Screen.BookList.route) {
                        BookListScreen(
                            books = viewModel.getBooks(),
                            onBookClick = { index ->
                                navController.navigate(Screen.ChapterList.createRoute(index))
                        },
                                onSearchClick = {
                                    navController.navigate(Screen.Search.route)

                                }
                        )
                    }

                    composable(
                        route = Screen.ChapterList.route,
                        arguments = listOf(navArgument("bookIndex") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val bookIndex = backStackEntry.arguments?.getInt("bookIndex") ?: 0
                        ChapterListScreen(
                            chapters = viewModel.getChapters(bookIndex),
                            onChapterClick = { chapterKey ->
                                navController.navigate(
                                    Screen.VerseList.createRoute(bookIndex, chapterKey)
                                )
                            }
                        )
                    }

                    composable(
                        route = Screen.VerseList.route,
                        arguments = listOf(
                            navArgument("bookIndex") { type = NavType.IntType },
                            navArgument("chapterKey") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val bookIndex = backStackEntry.arguments?.getInt("bookIndex") ?: 0
                        val chapterKey = backStackEntry.arguments?.getString("chapterKey") ?: "1"
                        VerseListScreen(
                            verses = viewModel.getVerses(bookIndex, chapterKey)
                        )
                    }

                    composable(Screen.Search.route) {
                        var searchResults by remember { mutableStateOf(listOf<SearchResult>()) }

                        SearchScreen(
                            results = searchResults,
                            onSearch = { query ->
                                searchResults = viewModel.searchVerses(query)
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }




                //when (screen) {


//                    Screen.ChapterList -> ChapterListScreen(
//                        chapters = viewModel.getChapters(selectedBookIndex),
//                        onChapterClick = {
//                            selectedChapterKey = it
//                            screen = Screen.VerseList
//                        }
//                    )

//                    Screen.VerseList -> VerseListScreen(
//                        verses = viewModel.getVerses(selectedBookIndex, selectedChapterKey)
//                    )

//                    Screen.Search -> SearchScreen(
//                        results = searchResults,
//                        onBack = { screen = Screen.BookList },
//                        onSearch = { query ->
//                            searchResults = viewModel.searchVerses(query)
//                        }
//                    )
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
               // }
            }
        }
    }
}