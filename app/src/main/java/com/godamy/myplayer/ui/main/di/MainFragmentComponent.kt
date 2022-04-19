package com.godamy.myplayer.ui.main.di

import com.godamy.myplayer.ui.main.MainViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    val mainViewModelFactory: MainViewModelFactory
}
