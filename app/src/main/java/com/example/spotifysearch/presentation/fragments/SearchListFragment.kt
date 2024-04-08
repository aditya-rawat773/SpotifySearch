package com.example.spotifysearch.presentation.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotifysearch.R
import com.example.spotifysearch.data.models.Album
import com.example.spotifysearch.data.models.Artist
import com.example.spotifysearch.data.models.Header
import com.example.spotifysearch.data.models.Playlist
import com.example.spotifysearch.data.models.Track
import com.example.spotifysearch.data.room.AppDatabase
import com.example.spotifysearch.databinding.FragmentSearchListBinding
import com.example.spotifysearch.presentation.adapters.SearchListAdapter
import com.example.spotifysearch.presentation.viewModels.SearchViewModel
import com.example.spotifysearch.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchListFragment : Fragment() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private lateinit var binding: FragmentSearchListBinding

    private val searchViewModel: SearchViewModel by viewModels()

    private val queryFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private lateinit var searchListAdapter: SearchListAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        clearListAndShowLocalList()
        searchQuery()
        observeData()
    }

    private fun setupRecyclerViews() {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context)
            searchListAdapter = SearchListAdapter { item ->
                onItemClickListener(item)
            }
            adapter = searchListAdapter
        }
    }

    private fun clearListAndShowLocalList() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchListAdapter.submitList(emptyList())
            val items: MutableList<Any> = mutableListOf()
            items.addAll(appDatabase.itemDao().getAllTracks())
            items.addAll(appDatabase.itemDao().getAllAlbums())
            items.addAll(appDatabase.itemDao().getAllArtists())
            items.addAll(appDatabase.itemDao().getAllPlaylists())
            searchListAdapter.submitList(items)

        }
    }

    private fun searchQuery() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    queryFlow.value = newText
                } else {
                    clearListAndShowLocalList()
                }
                return true
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            queryFlow.debounce(500L)
                .collectLatest { query ->
                    searchJob?.cancel()

                    searchJob = lifecycleScope.launch {
                        if (query != null) {
                            searchViewModel.fetchAlbumList(query)
                        }
                    }
                }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {

            searchViewModel.searchState.collect { it ->

                when (it.status) {
                    Status.LOADING -> {
                        Log.i("SearchListFragment", "Loading...")
                    }

                    Status.SUCCESS -> {
                        it.data?.let { response ->
                            response.let {

                                val items: MutableList<Any> = mutableListOf()

                                items.addAll(listOf(Header(title = "Tracks")))
                                items.addAll(response.tracks.items.filterNotNull())
                                items.addAll(listOf(Header(title = "Album")))
                                items.addAll(response.albums.items.filterNotNull())
                                items.addAll(listOf(Header(title = "Artist")))
                                items.addAll(response.artists.items.filterNotNull())
                                items.addAll(listOf(Header(title = "Playlist")))
                                items.addAll(response.playlists.items.filterNotNull())
                                searchListAdapter.submitList(items)
                            }
                        }
                    }

                    else -> {
                        Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
    }

    private fun onItemClickListener(item: Any) {

        val itemBundle = Bundle().apply {
            when (item) {
                is Album -> putSerializable("item", item)
                is Artist -> putSerializable("item", item)
                is Track -> putSerializable("item", item)
                is Playlist -> putSerializable("item", item)
            }
        }

        searchViewModel.insertItem(item)

        findNavController().navigate(
            R.id.action_searchListFragment_to_serachItemDetailFragment,
            itemBundle
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.cancel()
    }
}