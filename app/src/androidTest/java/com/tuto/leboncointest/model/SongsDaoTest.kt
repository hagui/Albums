package com.tuto.leboncointest.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.tuto.leboncointest.datasources.database.Database
import com.tuto.leboncointest.utils.getValue
import org.hamcrest.Matchers
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SongsDaoTest {
    private lateinit var database: Database
    private lateinit var songsDao: SongsDao

    private val song1 = Song(
        1,
        5,
        "",
        "lorem ipsum",
        "ImageUrl"
    )

    private val song2 = Song(
        1,
        45,
        "",
        "lorem toto",
        "Url"
    )


    private val song3 = Song(
        1,
        56,
        "",
        "lorem ",
        "Ul"
    )

    private val song4 = Song(
        2,
        56,
        "",
        "lorepl ",
        "Ulop"
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
        songsDao = database.songsDao()

        // Insert songss in non-alphabetical
        songsDao.insertAll(listOf(song1, song2, song3))
        // test replace if conflict
        songsDao.insertSong(song4)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGet() {
        val songList = getValue(songsDao.querySongs())
      //  assertThat(songList.value!!.size, Matchers.equalTo(3))
        assertThat(songList.size, Matchers.equalTo(3))
    }

    /*@Test fun testGetByAlbum() {
        assertThat(getValue(songsDao.querySongsByAlbum(song1.albumId)), Matchers.equalTo(song1))
    }*/
}