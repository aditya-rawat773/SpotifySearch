package com.example.spotifysearch.data.room

import androidx.room.TypeConverter
import com.example.spotifysearch.data.models.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromImageList(value: List<Image>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toImageList(value: String?): List<Image>? {
        val type = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(value, type)
    }
}