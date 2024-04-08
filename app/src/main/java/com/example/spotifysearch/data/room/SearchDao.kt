package com.example.spotifysearch.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spotifysearch.data.models.Album
import com.example.spotifysearch.data.models.Artist
import com.example.spotifysearch.data.models.Playlist
import com.example.spotifysearch.data.models.Track

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: Artist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Query("SELECT * FROM album")
    suspend fun getAllAlbums(): List<Album>

    @Query("SELECT * FROM artist")
    suspend fun getAllArtists(): List<Artist>

    @Query("SELECT * FROM track")
    suspend fun getAllTracks(): List<Track>

    @Query("SELECT * FROM playlist")
    suspend fun getAllPlaylists(): List<Playlist>


}
