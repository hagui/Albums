package com.tuto.leboncointest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuto.leboncointest.model.Song
import com.tuto.leboncointest.model.SongsRepository

class SongDetailViewModel(songsRepository: SongsRepository, private val songId: Int) : ViewModel() {
    val  song: LiveData<Song>

    init {
       song = songsRepository.getSong(songId)
    }
}