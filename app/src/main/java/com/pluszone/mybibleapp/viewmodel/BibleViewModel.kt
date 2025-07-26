package com.pluszone.mybibleapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.pluszone.mybibleapp.data.model.Book
import com.pluszone.mybibleapp.data.model.SearchResult
import com.pluszone.mybibleapp.data.repository.BibleRepository


class BibleViewModel(context: Context) : ViewModel() {
    private val bible = BibleRepository(context).bible
    private val bookNames = bible.keys.toList()

    fun getBooks(): List<String> = bookNames

    fun getChapters(bookIndex: Int): List<String> {
        val bookName = bookNames[bookIndex]
        return bible[bookName]?.keys?.sortedBy { it.toInt() } ?: emptyList()
    }

    fun getVerses(bookIndex: Int, chapterKey: String): List<Pair<String, String>> {
        val bookName = bookNames[bookIndex]
        val verses = bible[bookName]?.get(chapterKey) ?: return emptyList()
        return verses.toSortedMap(compareBy { it.toInt() }).toList()
    }

    fun searchVerses(query: String): List<SearchResult> {
        if (query.isBlank()) return emptyList()

        return bible.flatMap { (bookName, chapters) ->
            chapters.flatMap { (chapterNum, verses) ->
                verses.filter { (_, verseText) ->
                    verseText.contains(query, ignoreCase = true)
                }.map { (verseNum, verseText) ->
                    SearchResult(bookName, chapterNum, verseNum, verseText)
                }
            }
        }
    }
}