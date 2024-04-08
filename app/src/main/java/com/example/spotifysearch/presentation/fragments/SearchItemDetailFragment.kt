package com.example.spotifysearch.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spotifysearch.data.models.Album
import com.example.spotifysearch.data.models.Artist
import com.example.spotifysearch.data.models.Playlist
import com.example.spotifysearch.data.models.Track
import com.example.spotifysearch.databinding.FragmentSearchItemDetailBinding

class SearchItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchItemDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchItemDetailBinding.inflate(inflater, container, false)

        when (val item = requireArguments().get("item")) {
            is Album -> {
                binding.title.text = item.name
            }

            is Artist -> {
                binding.title.text = item.name

            }

            is Track -> {
                binding.title.text = item.name
            }

            is Playlist -> {
                binding.title.text = item.name
            }
        }
        return binding.root
    }
}