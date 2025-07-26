package com.pluszone.mybibleapp.data.repository

import android.content.Context
import com.pluszone.mybibleapp.data.model.Bible
import com.pluszone.mybibleapp.utils.JsonLoader

class BibleRepository(context: Context) {
    val bible: Bible = JsonLoader.loadBible(context)
}