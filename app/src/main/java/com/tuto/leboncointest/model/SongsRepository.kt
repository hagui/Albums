package com.tuto.leboncointest.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.tuto.leboncointest.datasources.database.NetworkBoundResource
import com.tuto.leboncointest.datasources.webservice.Resource
import com.tuto.leboncointest.datasources.webservice.WebService
import com.tuto.leboncointest.utils.AppExecutors
import com.tuto.leboncointest.utils.Utils
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SongsRepository @Inject constructor(
        val webService: WebService,
        val songsDao: SongsDao,
        val utils: Utils,
        val appExecutors: AppExecutors
) {
    val TAG = "songsRepository"

    open fun getSongs(id: Int = 0): LiveData<Resource<List<Song>>> {
        return object : NetworkBoundResource<List<Song>>(appExecutors) {

            override fun saveNetworkCallResult(data: List<Song>?) {
                data?.filterNot {
                    (
                            it.title.isNullOrBlank())
                }?.forEach { songsDao.insertSong(it) }
            }

            override fun shouldLoadFromNetwork(data: List<Song>?): Boolean {
                val shouldLoadFromNetwork = utils.hasConnection() && (data == null || data.isEmpty())
                Log.d(TAG, "shouldLoadFromNetwork: $shouldLoadFromNetwork")
                return shouldLoadFromNetwork
            }

            override fun loadFromDatabase(): LiveData<List<Song>> {
                Log.d(TAG, "loadFromDatabase")
                return when (id == 0) {
                    true -> songsDao.querySongs()
                    else -> songsDao.querySongsByAlbum(id)
                }
            }

            override fun createNetworkCall(): Call<List<Song>> {
                Log.d(TAG, "createNetworkCall")
                return webService.getAlbums()
            }
        }.asLiveData()
    }

    open fun getSong(id: Int) = songsDao.querySongById(id)
    //TODO For Singleton instantiation
   /* companion object {


        @Volatile private var instance: SongsRepository? = null

        fun getInstance(songsDao: SongsDao) =
            instance ?: synchronized(this) {
                instance ?: SongsRepository(webService,songsDao, ).also { instance = it }
            }
    }*/
}