package com.godamy.myplayer.di

import android.app.Application
import com.godamy.myplayer.ui.detail.DetailFragment
import com.godamy.myplayer.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, AppDataModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    fun inject(mainFragment: MainFragment)
    fun inject(detailFragment: DetailFragment)
}
