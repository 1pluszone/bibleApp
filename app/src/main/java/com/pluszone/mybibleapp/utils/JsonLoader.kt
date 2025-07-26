package com.pluszone.mybibleapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pluszone.mybibleapp.data.model.Bible
import java.io.InputStreamReader


object JsonLoader {
    fun loadBible(context: Context): Bible {
            val inputStream = context.assets.open("KJV_bible.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<Bible>() {}.type
            return Gson().fromJson(reader, type)

        }
}