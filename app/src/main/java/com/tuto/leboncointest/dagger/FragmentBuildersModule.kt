package com.tuto.leboncointest.dagger

import com.tuto.leboncointest.view.SongDetailFragment
import com.tuto.leboncointest.view.SongListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSongListFragment(): SongListFragment

    @ContributesAndroidInjector
    abstract fun contributeSongDetailFragment(): SongDetailFragment
}