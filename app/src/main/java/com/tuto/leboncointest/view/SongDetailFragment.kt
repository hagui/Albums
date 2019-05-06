package com.tuto.leboncointest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.tuto.leboncointest.R
import com.tuto.leboncointest.dagger.Injectable
import com.tuto.leboncointest.databinding.FragmentSongDetailBinding
import com.tuto.leboncointest.viewmodels.SongDetailViewModel
import com.tuto.leboncointest.viewmodels.ViewModelFactory
import javax.inject.Inject


class SongDetailFragment : Fragment(), Injectable {

    private val args: SongDetailFragmentArgs by navArgs()


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var songDetailViewModel: SongDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      /*   songDetailViewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(songDetailViewModel::class.java)*/

        val binding = DataBindingUtil.inflate<FragmentSongDetailBinding>(
            inflater, R.layout.fragment_song_detail, container, false
        )/*.apply {
            viewModel = songDetailViewModel
            setLifecycleOwner(this@SongDetailFragment)
            fab.setOnClickListener { view ->
                Snackbar.make(view, "add to vaf", Snackbar.LENGTH_LONG).show()
            }
        }

        songDetailViewModel.song.observe(this, Observer {

        })*/


        setHasOptionsMenu(true)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  songDetailViewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(songDetailViewModel::class.java)*/

    }
}
