package com.godamy.myplayer.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.godamy.myplayer.di.MediaItemId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @MediaItemId
    fun provideMediaItemId(savedStateHandle: SavedStateHandle): Int =
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).mediaItemId
}
