package com.pluszone.mybibleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                            onBookClick = { bookIndex, bookName ->
                                navController.navigate(Screen.ChapterList.createRoute(bookIndex, bookName))
                        },
                                onSearchClick = {
                                    navController.navigate(Screen.Search.route)

                                }
                        )
                    }

                    composable(
                        route = Screen.ChapterList.route,
                        arguments = listOf(
                            navArgument("bookIndex") { type = NavType.IntType },
                            navArgument("bookName") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val bookIndex = backStackEntry.arguments?.getInt("bookIndex") ?: 0
                        val bookName = backStackEntry.arguments?.getString("bookName") ?: ""

                        ChapterListScreen(
                            title = bookName,
                            chapters = viewModel.getChapters(bookIndex),
                            onChapterClick = { chapterKey ->
                                navController.navigate(
                                    Screen.VerseList.createRoute(bookIndex, bookName, chapterKey)
                                )
                            }
                        )
                    }

                    composable(
                        route = Screen.VerseList.route,
                        arguments = listOf(
                            navArgument("bookIndex") { type = NavType.IntType },
                            navArgument("bookName") {type = NavType.StringType },
                            navArgument("chapterKey") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val bookIndex = backStackEntry.arguments?.getInt("bookIndex") ?: 0
                        val bookName = backStackEntry.arguments?.getString("bookName") ?: ""
                        val chapterKey = backStackEntry.arguments?.getString("chapterKey") ?: "1"

                        VerseListScreen(
                            title = "$bookName $chapterKey",
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

            }
        }
    }
}