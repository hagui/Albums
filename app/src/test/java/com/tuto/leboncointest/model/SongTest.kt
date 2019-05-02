package com.tuto.leboncointest.model

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class SongTest {
    private lateinit var song: Song

    @Before fun setUp(){
        song = Song(1,
        2,
        "url",
        "lorem",
        "url")
    }

    @Test fun test_default_values() {
        val defaultSong = Song(1, 2, "","Description", "ImageURL")
        assertEquals(2, defaultSong.id)
        assertEquals("", defaultSong.thumbnailUrl)
    }


    @Test fun test_toString(){
        assertEquals("lorem" , song.toString())
    }

}