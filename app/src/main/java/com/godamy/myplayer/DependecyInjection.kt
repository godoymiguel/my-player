package com.godamy.myplayer

import android.app.Application
import androidx.room.Room
import com.godamy.myplayer.data.MediaRepository
import com.godamy.myplayer.data.PermissionChecker
import com.godamy.myplayer.data.RegionRepository
import com.godamy.myplayer.data.datasource.LocationDataSource
import com.godamy.myplayer.data.datasource.MediaItemLocalDataSource
import com.godamy.myplayer.data.datasource.MediaItemRemoteDataSource
import com.godamy.myplayer.framework.AndroidPermissionChecker
import com.godamy.myplayer.framework.PlayServiceLocationDataSource
import com.godamy.myplayer.framework.database.MediaItemDataBase
import com.godamy.myplayer.framework.database.MediaItemRoomDataSource
import com.godamy.myplayer.framework.server.MediaItemServerDataSource
import com.godamy.myplayer.ui.detail.DetailViewModel
import com.godamy.myplayer.ui.main.MainViewModel
import com.godamy.myplayer.usecases.FindMovieUseCase
import com.godamy.myplayer.usecases.GetPopularMoviesUserCase
import com.godamy.myplayer.usecases.RequestPopularMoviesUseCase
import com.godamy.myplayer.usecases.SwitchFavoriteUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(appModule, dataModule, useCasesModule)
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single {
        Room.databaseBuilder(
            get(),
            MediaItemDataBase::class.java,
            "media-item-db"
        ).build()
    }
    single { get<MediaItemDataBase>().mediaItemDao() }

    factory<LocationDataSource> { PlayServiceLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    factory<MediaItemRemoteDataSource> { MediaItemServerDataSource(get(named("apiKey"))) }
    factory<MediaItemLocalDataSource> { MediaItemRoomDataSource(get()) }

    viewModel { MainViewModel(get(), get()) }
    viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MediaRepository(get(), get(), get()) }
}

private val useCasesModule = module {
    factory { GetPopularMoviesUserCase(get()) }
    factory { RequestPopularMoviesUseCase(get()) }
    factory { FindMovieUseCase(get()) }
    factory { SwitchFavoriteUseCase(get()) }
}
