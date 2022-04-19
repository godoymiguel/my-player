package com.godamy.myplayer.di

import android.app.Application
import com.godamy.myplayer.ui.detail.di.DetailFragmentComponent
import com.godamy.myplayer.ui.detail.di.DetailFragmentModule
import com.godamy.myplayer.ui.main.di.MainFragmentComponent
import com.godamy.myplayer.ui.main.di.MainFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        UserCaseModule::class
    ]
)
interface AppComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule): DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}
