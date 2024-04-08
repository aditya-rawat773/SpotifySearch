package com.example.spotifysearch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spotifysearch.data.models.Album
import com.example.spotifysearch.data.models.Artist
import com.example.spotifysearch.data.models.Playlist
import com.example.spotifysearch.data.models.Track

@Database(entities = [Album::class,Artist::class,Track::class,Playlist::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): SearchDao
}
