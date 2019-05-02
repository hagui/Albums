package com.tuto.leboncointest.datasources.webservice

import com.tuto.leboncointest.model.Song
import retrofit2.Call
import retrofit2.http.GET


interface WebService {

    @GET("/technical-test.json")
    fun getAlbums(): Call<List<Song>>
}