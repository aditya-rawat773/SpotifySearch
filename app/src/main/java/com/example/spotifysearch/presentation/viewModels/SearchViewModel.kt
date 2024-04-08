package com.example.spotifysearch.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifysearch.data.models.Album
import com.example.spotifysearch.data.models.Artist
import com.example.spotifysearch.data.models.Header
import com.example.spotifysearch.data.models.Playlist
import com.example.spotifysearch.data.models.SearchResult
import com.example.spotifysearch.data.models.Track
import com.example.spotifysearch.data.repositories.SearchRepository
import com.example.spotifysearch.data.room.AppDatabase
import com.example.spotifysearch.util.Status
import com.example.spotifysearch.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository, private val appDatabase: AppDatabase): ViewModel(){

    val searchState = MutableStateFlow(
        ViewState(
            Status.LOADING,
            SearchResult(), ""
        )
    )

    fun insertItem(item: Any) {
        viewModelScope.launch {
            when (item) {
                is Album -> appDatabase.itemDao().insertAlbum(item)
                is Artist -> appDatabase.itemDao().insertArtist(item)
                is Track -> appDatabase.itemDao().insertTrack(item)
                is Playlist -> appDatabase.itemDao().insertPlaylist(item)
            }
        }
    }

    fun fetchAlbumList(q: String) {
        viewModelScope.launch {
            try {
               val result=  searchRepository.fetchAlbumsList(q)
                searchState.value = ViewState.success(result)
            } catch(ex:Exception) {
                searchState.value =
                        ViewState.error(ex.message.toString())
            }

        }
    }


}