package com.pluszone.mybibleapp.data.model

typealias Bible = Map<String, Map<String, Map<String, String>>>


data class Book(
    val name: String,
    val chapters: List<List<String>> // Each chapter is a list of verses
)