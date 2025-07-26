package com.pluszone.mybibleapp.ui

sealed class Screen(val route: String) {
    object BookList : Screen("books")
    object ChapterList : Screen("chapters/{bookIndex}/{bookName}") {
        fun createRoute(bookIndex: Int, bookName: String) = "chapters/$bookIndex/$bookName"
    }
    object VerseList : Screen("verses/{bookIndex}/{bookName}/{chapterKey}") {
        fun createRoute(bookIndex: Int, bookName: String, chapterKey: String) =
            "verses/$bookIndex/$bookName/$chapterKey"
    }
    object Search : Screen("search")

}