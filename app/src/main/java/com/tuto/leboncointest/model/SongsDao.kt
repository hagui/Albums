package com.tuto.leboncointest.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface SongsDao {

    @Query("SELECT * FROM songs")
    fun querySongs(): LiveData<List<Song>>

    @Query("SELECT * FROM songs WHERE albumId LIKE :albumId")
    fun querySongsByAlbum(albumId :Int): LiveData<List<Song>>

    @Insert(onConflict = REPLACE)
    fun insertSong(song: Song)

    @Insert(onConflict = REPLACE)
    fun insertAll(songs: List<Song>)
}