package com.example.spotifysearch.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifysearch.data.models.Album
import com.example.spotifysearch.data.models.Artist
import com.example.spotifysearch.data.models.Header
import com.example.spotifysearch.data.models.Playlist
import com.example.spotifysearch.data.models.Track
import com.example.spotifysearch.databinding.ItemHeaderBinding
import com.example.spotifysearch.databinding.ItemListBinding

class SearchListAdapter(private val onItemClickListener: (Any) -> Unit) :
    ListAdapter<Any, RecyclerView.ViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ALBUM -> {
                val binding = ItemListBinding.inflate(inflater, parent, false)
                AlbumViewHolder(binding)
            }

            VIEW_TYPE_ARTIST -> {
                val binding = ItemListBinding.inflate(inflater, parent, false)
                ArtistViewHolder(binding)
            }

            VIEW_TYPE_TRACK -> {
                val binding = ItemListBinding.inflate(inflater, parent, false)
                TrackViewHolder(binding)
            }

            VIEW_TYPE_PLAYLIST -> {
                val binding = ItemListBinding.inflate(inflater, parent, false)
                PlayListViewHolder(binding)
            }

            VIEW_TYPE_HEADER -> {
                val binding = ItemHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is AlbumViewHolder -> holder.bind(item as Album)
            is ArtistViewHolder -> holder.bind(item as Artist)
            is TrackViewHolder -> holder.bind(item as Track)
            is PlayListViewHolder -> holder.bind(item as Playlist)
            is HeaderViewHolder -> holder.bind(item as Header)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Album -> VIEW_TYPE_ALBUM
            is Artist -> VIEW_TYPE_ARTIST
            is Track -> VIEW_TYPE_TRACK
            is Playlist -> VIEW_TYPE_PLAYLIST
            is Header -> VIEW_TYPE_HEADER
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    inner class AlbumViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album) {
            binding.apply {
                root.setOnClickListener { onItemClickListener.invoke(item) }
                headerTitle.text = item.name
            }
        }
    }

    inner class ArtistViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Artist) {
            binding.apply {
                root.setOnClickListener { onItemClickListener.invoke(item) }
                headerTitle.text = item.name
            }
        }
    }

    inner class TrackViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Track) {
            binding.apply {
                root.setOnClickListener { onItemClickListener.invoke(item) }
                headerTitle.text = item.name
            }
        }
    }

    inner class PlayListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Playlist) {
            binding.apply {
                root.setOnClickListener { onItemClickListener.invoke(item) }
                headerTitle.text = item.name
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Header) {
            binding.apply {
                header.text = item.title
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_ALBUM = 1
        private const val VIEW_TYPE_ARTIST = 2
        private const val VIEW_TYPE_TRACK = 3
        private const val VIEW_TYPE_PLAYLIST = 4
        private const val VIEW_TYPE_HEADER = 5
    }
}

private class SearchDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is Album && newItem is Album -> oldItem.id == newItem.id
            oldItem is Artist && newItem is Artist -> oldItem.id == newItem.id
            oldItem is Track && newItem is Track -> oldItem.id == newItem.id
            oldItem is Playlist && newItem is Playlist -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is Album && newItem is Album -> oldItem.id == newItem.id
            oldItem is Artist && newItem is Artist -> oldItem.id == newItem.id
            oldItem is Track && newItem is Track -> oldItem.id == newItem.id
            oldItem is Playlist && newItem is Playlist -> oldItem.id == newItem.id
            else -> false
        }
    }
}

