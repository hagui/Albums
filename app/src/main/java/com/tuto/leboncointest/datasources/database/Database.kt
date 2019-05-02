package com.tuto.leboncointest.datasources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tuto.leboncointest.model.Song
import com.tuto.leboncointest.model.SongsDao


@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract  class Database : RoomDatabase(){
    abstract fun songsDao(): SongsDao
}