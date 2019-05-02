package com.tuto.leboncointest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tuto.leboncointest.datasources.webservice.Resource
import com.tuto.leboncointest.model.Song
import com.tuto.leboncointest.model.SongsRepository
import javax.inject.Inject

class AlbumsViewModel @Inject constructor(private val repository: SongsRepository) : ViewModel() {

    var initialized = false

    var songsInput: MutableLiveData<Int> = MutableLiveData()

    val songs: LiveData<Resource<List<Song>>> = Transformations.switchMap(songsInput) { song ->
        initialized = true; repository.getSongs(song)
    }

}