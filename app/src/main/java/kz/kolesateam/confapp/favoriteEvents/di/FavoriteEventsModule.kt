package kz.kolesateam.confapp.favoriteEvents.di

import kz.kolesateam.confapp.favoriteEvents.data.DefaultFavoriteEventsRepository
import kz.kolesateam.confapp.favoriteEvents.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.favoriteEvents.presentation.FavoriteEventsRouter
import kz.kolesateam.confapp.favoriteEvents.presentation.viewModel.FavoriteEventsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteEventsModule = module {

    viewModel {
        FavoriteEventsViewModel(
            favoriteEventsRepository = get(),
            notificationAlarmHelper = get()
        )
    }

    single {
        DefaultFavoriteEventsRepository(
            context = androidApplication(),
            objectMapper = get(),
            eventApiDataMapper = get()
        ) as FavoriteEventsRepository
    }

    factory {
        FavoriteEventsRouter()
    }
}