package com.example.spotifysearch.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResult(
    @SerializedName("albums")
    val albums: SearchResultType<Album?> = SearchResultType(),
    @SerializedName("artists")
    val artists: SearchResultType<Artist?> = SearchResultType(),
    @SerializedName("tracks")
    val tracks: SearchResultType<Track?> = SearchResultType(),
    @SerializedName("playlists")
    val playlists: SearchResultType<Playlist?> = SearchResultType()
)

data class SearchResultType<T>(
    @SerializedName("href")
    val href: String? = null,
    @SerializedName("items")
    val items: List<T?> = emptyList(),
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("total")
    val total: Int = 0
)

@Entity(tableName = "album")
data class Album(
    @SerializedName("albumType")
    val albumType: String? = null,
    @SerializedName("href")
    val href: String? = null,
    @SerializedName("id")
    @PrimaryKey
    val id: String = "",
    @SerializedName("images")
    val images: List<Image?> = emptyList(),
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("releaseDate")
    val releaseDate: String? = null,
    @SerializedName("releaseDatePrecision")
    val releaseDatePrecision: String? = null,
    @SerializedName("totalTracks")
    val totalTracks: Int? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("uri")
    val uri: String? = null
):Serializable

@Entity(tableName = "artist")
data class Artist(
    @SerializedName("href")
    val href: String? = null,
    @SerializedName("id")
    @PrimaryKey
    val id: String = "",
    @SerializedName("images")
    val images: List<Image?> = emptyList(),
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("popularity")
    val popularity: Int? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("uri")
    val uri: String? = null
):Serializable

@Entity(tableName = "track")
data class Track(
    @SerializedName("discNumber")
    val discNumber: Int? = null,
    @SerializedName("durationMs")
    val durationMs: Long? = null,
    @SerializedName("href")
    val href: String? = null,
    @SerializedName("id")
    @PrimaryKey
    val id: String = "",
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("popularity")
    val popularity: Int? = null,
    @SerializedName("previewUrl")
    val previewUrl: String? = null,
    @SerializedName("trackNumber")
    val trackNumber: Int? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("uri")
    val uri: String? = null
):Serializable

@Entity(tableName = "playlist")
data class Playlist(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("href")
    val href: String? = null,
    @SerializedName("id")
    @PrimaryKey
    val id: String = "",
    @SerializedName("images")
    val images: List<Image?> = emptyList(),
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("primaryColor")
    val primaryColor: String? = null,
    @SerializedName("snapshotId")
    val snapshotId: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("uri")
    val uri: String? = null
):Serializable


data class Image(
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("width")
    val width: Int? = null
)




