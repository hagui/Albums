package com.tuto.leboncointest.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tuto.leboncointest.adapters.SongAdapter
import com.tuto.leboncointest.dagger.Injectable
import com.tuto.leboncointest.databinding.FragmentSongListBinding
import com.tuto.leboncointest.model.Song
import com.tuto.leboncointest.utils.ResourceObserver
import com.tuto.leboncointest.utils.Utils
import com.tuto.leboncointest.viewmodels.AlbumsViewModel
import com.tuto.leboncointest.viewmodels.ViewModelFactory
import javax.inject.Inject


class SongListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var utils: Utils
    private lateinit var albumsViewModel: AlbumsViewModel
    private  var adapter = SongAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSongListBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root
         //val adapter = SongAdapter()
        //  subscribeUi()
        albumsViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumsViewModel::class.java)
        subscribeUi()

        binding.songList.adapter = adapter
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


    private fun subscribeUi() {
        albumsViewModel.songsInput.value = 1

        albumsViewModel.songs.observe(
            this, ResourceObserver(
                tag = "RestaurantsMapActivity",
                hideLoading = ::hideLoading,
                showLoading = ::showLoading,
                onSuccess = ::showList,
                onError = ::showErrorMessage
            )
        )
    }

    private fun showList(list: List<Song>) {
        adapter.submitList(list)
    }

    private fun showErrorMessage(error: String) {
        println(" fail $error")
    }

    private fun showLoading() {
        //  progressbar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        //progressbar.visibility = View.GONE
    }


}
