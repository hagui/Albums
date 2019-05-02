package com.tuto.leboncointest.datasources.database

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tuto.leboncointest.datasources.webservice.MyError
import com.tuto.leboncointest.datasources.webservice.Resource
import com.tuto.leboncointest.utils.AppExecutors
import retrofit2.Call
import java.io.IOException


abstract class NetworkBoundResource<T>(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<T>>()

    init {
        result.value = Resource.loading(null)
        val dbSource = loadFromDatabase()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldLoadFromNetwork(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<T>) {

        appExecutors.networkIO().execute {

            try {
                val response = createNetworkCall().execute()

                println("response is: $response")

                when (response.isSuccessful) {
                    true -> appExecutors.diskIO().execute {
                        saveNetworkCallResult(response.body())

                        appExecutors.mainThread().execute {
                            val newDbSource = loadFromDatabase()
                            result.addSource(newDbSource) { newData ->
                                result.removeSource(newDbSource)
                                result.setValue(Resource.success(newData))
                            }
                        }
                    }

                    false -> appExecutors.mainThread().execute {
                        result.addSource(dbSource) { newData -> result.setValue(Resource.error(newData, MyError(response.code(), response.message()))) }
                    }

                }
            } catch (exc: IOException) {
                appExecutors.mainThread().execute {
                    result.addSource(dbSource) { newData -> result.setValue(Resource.error(newData, MyError(503, "Service Unavailable."))) }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<T>> = result

    @WorkerThread
    protected abstract fun saveNetworkCallResult(data: T?)

    @MainThread
    protected abstract fun shouldLoadFromNetwork(data: T?): Boolean

    @MainThread
    protected abstract fun loadFromDatabase(): LiveData<T>

    @WorkerThread
    protected abstract fun createNetworkCall(): Call<T>
}