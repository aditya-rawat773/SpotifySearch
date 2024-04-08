package com.example.spotifysearch.data.datasources

import com.example.spotifysearch.data.models.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

/*http GET 'https://api.spotify.com/v1/search?q=talwin&type=album&market=IN'*/
/*'https://api.spotify.com/v1/search?q=tal&type=album%2Cartist%2Ctrack%2Cplaylist&market=IN'*/
interface SearchService {
    @GET("search")
    suspend fun getAlbumsList(
        @Query("q") q: String,
        @Query("type") type: String,
        @Query("market") market: String
    ): SearchResult
}

