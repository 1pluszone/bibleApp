package com.pluszone.mybibleapp.ui

sealed class Screen(val route: String) {
    object BookList : Screen("books")
    object ChapterList : Screen("chapters/{bookIndex}") {
        fun createRoute(bookIndex: Int) = "chapters/$bookIndex"
    }
    object VerseList : Screen("verses/{bookIndex}/{chapterKey}") {
        fun createRoute(bookIndex: Int, chapterKey: String) =
            "verses/$bookIndex/$chapterKey"
    }
    object Search : Screen("search")

}