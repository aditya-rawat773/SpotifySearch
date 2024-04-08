package com.example.spotifysearch.data.repositories

import com.example.spotifysearch.data.datasources.SearchService
import com.example.spotifysearch.data.models.SearchResult
import java.lang.Exception
import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchService: SearchService) {
    suspend fun fetchAlbumsList(q: String): SearchResult{
        try {
            return searchService.getAlbumsList(q, "album,artist,track,playlist", "IN")
        } catch (ex: Exception) {
            throw ex
        }
    }

}