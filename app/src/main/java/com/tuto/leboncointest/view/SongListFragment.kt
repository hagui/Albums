package com.tuto.leboncointest.view

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tuto.leboncointest.adapters.SongAdapter
import com.tuto.leboncointest.dagger.Injectable
import com.tuto.leboncointest.databinding.FragmentSongListBinding
import com.tuto.leboncointest.model.Song
import com.tuto.leboncointest.utils.ResourceObserver
import com.tuto.leboncointest.utils.Utils
import com.tuto.leboncointest.utils.autoCleared
import com.tuto.leboncointest.viewmodels.AlbumsViewModel
import com.tuto.leboncointest.viewmodels.ViewModelFactory
import javax.inject.Inject


class SongListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var utils: Utils
    private lateinit var albumsViewModel: AlbumsViewModel
    private var adapter = SongAdapter()

    var bindings by autoCleared<FragmentSongListBinding>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSongListBinding.inflate(inflater, container, false)
        albumsViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumsViewModel::class.java)
        subscribeUi()

        binding.songList.adapter = adapter
        bindings = binding
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initSearchInputListener()
    }


    private fun subscribeUi() {
        albumsViewModel.songsInput.value = 0

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

    //TODO gestion des resultat de recherche apres une rotation
    private fun initSearchInputListener() {
        bindings.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        bindings.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = bindings.input.text.toString()
        // Dismiss keyboard
        val parsedInt = query.toIntOrNull()
        query.toIntOrNull()?.let {
            albumsViewModel.songsInput.value = parsedInt
        }
        dismissKeyboard(v.windowToken)

    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun showList(list: List<Song>) {
        adapter.submitList(list)
    }

    private fun showErrorMessage(error: String) {
        println(" fail $error")
    }

    private fun showLoading() {
        //  progressbar.visibility = View.VISIBLE
        bindings.loadingMore = true
    }

    private fun hideLoading() {
        bindings.loadingMore = false
    }


}
