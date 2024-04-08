package com.example.spotifysearch

import com.example.spotifysearch.data.models.SearchResult
import com.example.spotifysearch.data.repositories.SearchRepository
import com.example.spotifysearch.presentation.viewModels.SearchViewModel
import com.example.spotifysearch.util.Status
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    @MockK
    lateinit var searchRepository: SearchRepository
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        viewModel = SearchViewModel(searchRepository)
    }
    @Test
    fun `fetchAlbumList check if searchState changes to error on failure`(){
        runTest {
            val result = SearchResult()
            coEvery { searchRepository.fetchAlbumsList(any()) }throws ApiFailException()
            async {
                val emission = viewModel.searchState.take(2).toList()
                for (em in emission){
                    println(em.status)
                }
                assert(viewModel.searchState.value.status == Status.ERROR )
            }
            delay(200) // delay so emission can get init before the call
            viewModel.fetchAlbumList("error test")

        }

    }
    @Test
    fun `fetchAlbumList check if searchState change to ViewState success on api success`(){
        runTest {
            val result = SearchResult()
            coEvery { searchRepository.fetchAlbumsList(any()) } returns result
            async {
                val emission = viewModel.searchState.take(2).toList()
                for (em in emission){
                    println(em.status)
                }
                assert(viewModel.searchState.value.status == Status.SUCCESS )
            }
            delay(200)
            viewModel.fetchAlbumList("test success")

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun afterSetup(){
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

}
class ApiFailException : Exception()
