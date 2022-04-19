package com.godamy.myplayer.di

import android.app.Application
import com.godamy.myplayer.ui.detail.DetailViewModelFactory
import com.godamy.myplayer.ui.main.MainViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        UserCaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    val mainViewModelFactory: MainViewModelFactory
    val detailViewModelFactory: DetailViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}
