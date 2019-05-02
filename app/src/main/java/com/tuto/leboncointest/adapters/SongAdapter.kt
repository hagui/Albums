package com.tuto.leboncointest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuto.leboncointest.databinding.ListItemSongBinding
import com.tuto.leboncointest.model.Song
import com.tuto.leboncointest.view.SongListFragmentDirections

class SongAdapter : ListAdapter<Song, SongAdapter.ViewHolder>(SongDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = getItem(position)
        holder.apply {
            bind(createOnClickListener(song.id), song)
            itemView.tag = song
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemSongBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(songId: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction = SongListFragmentDirections.actionSongListFragmentToSongDetailFragment(songId)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Song) {
            binding.apply {
                clickListener = listener
                song = item
                executePendingBindings()
            }
        }
    }
}

private class SongDiffCallback : DiffUtil.ItemCallback<Song>() {

    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }
}