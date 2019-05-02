package com.tuto.leboncointest.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "songs")
data class Song(
    val albumId: Int,
    @PrimaryKey val id: Int,
    val thumbnailUrl: String="",
    val title: String,
    val url: String = ""
)