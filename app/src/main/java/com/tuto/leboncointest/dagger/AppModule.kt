package com.tuto.leboncointest.dagger

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.tuto.leboncointest.datasources.webservice.WebService
import com.tuto.leboncointest.model.SongsDao
import com.tuto.leboncointest.utils.Utils
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module (includes = [ViewModelModule::class])
class AppModule{

    @Singleton
    @Provides
    fun provideWebService(): WebService {
        return Retrofit.Builder()
                .baseUrl("https://static.leboncoin.fr/img/shared/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WebService::class.java)
    }



    /*@Provides
    @Singleton
    fun provideApplication(app: Application): Application = app*/

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun providesUtils(app: Application): Utils = Utils(app)

    @Provides
    @Singleton
    fun provideSongsDatabase(app: Application): com.tuto.leboncointest.datasources.database.Database =
        Room.databaseBuilder(app , com.tuto.leboncointest.datasources.database.Database ::class.java , "song_db").build()
       // Room.databaseBuilder(app, Database::class.java, "song_db").build()

    @Provides
    @Singleton
    fun provideSongsDao(db: com.tuto.leboncointest.datasources.database.Database): SongsDao = db.songsDao()



}