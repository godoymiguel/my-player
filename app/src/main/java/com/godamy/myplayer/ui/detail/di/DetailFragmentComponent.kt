package com.godamy.myplayer.ui.detail.di

import com.godamy.myplayer.ui.detail.DetailViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent {
    val detailViewModelFactory: DetailViewModelFactory
}
