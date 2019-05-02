package com.tuto.leboncointest.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuto.leboncointest.viewmodels.AlbumsViewModel
import com.tuto.leboncointest.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumsViewModel::class)
    internal abstract fun bindAlbumsViewModel(albumsViewModel: AlbumsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}