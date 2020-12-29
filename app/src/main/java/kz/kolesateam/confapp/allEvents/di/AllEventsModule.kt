package kz.kolesateam.confapp.allEvents.di

import kz.kolesateam.confapp.allEvents.data.AllEventsDataSource
import kz.kolesateam.confapp.allEvents.data.DefaultAllEventsRepository
import kz.kolesateam.confapp.allEvents.domain.AllEventsRepository
import kz.kolesateam.confapp.allEvents.presentation.AllEventsRouter
import kz.kolesateam.confapp.allEvents.presentation.viewModel.AllEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val allEventsModule = module {

    viewModel {
        AllEventsViewModel(
            allEventsRepository = get(),
            favoriteEventsRepository = get(),
            notificationAlarmHelper = get()
        )
    }

    single {
        val retrofit = get<Retrofit>()

        retrofit.create(AllEventsDataSource::class.java)
    }

    factory {
        DefaultAllEventsRepository(
            allEventsDataSource = get(),
            eventApiDataMapper = get()
        ) as AllEventsRepository
    }

    factory {
        AllEventsRouter()
    }
}